package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.PageResult;
import com.itheima.entity.Dept;
import com.itheima.entity.User;
import com.itheima.exception.QkException;
import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addDept(Dept dept) {

        //1.验证"部门名称"是否存在
        Dept deptResult = deptMapper.selectOne(
                Wrappers.lambdaQuery(Dept.class)
                        .eq(Dept::getName, dept.getName())
        );
        if (deptResult != null) {
            //抛异常 ——> QkException
            throw new QkException("【错误】添加部门时发生错误：部门名称已存在！");
        }
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        int row = deptMapper.insert(dept);
        return row == 1;
    }

    @Override
    public PageResult<Dept> deptList(String name, Integer status, Integer page, Integer pageSize) {
        Page<Dept> deptPage = deptMapper.selectPage(
                new Page<>(page, pageSize),
                Wrappers.lambdaQuery(Dept.class)
                        .like(StringUtils.isNotBlank(name), Dept::getName, name)
                        .eq(status != null, Dept::getStatus, status)
        );
        //将deptPage对象转换为PageResult对象
        PageResult<Dept> pageResult = new PageResult<>();
        pageResult.setTotal(deptPage.getTotal());
        pageResult.setRows(deptPage.getRecords());

        return pageResult;
    }

    @Override
    public Dept findById(Integer id) {
        //直接调用持久层
        return this.deptMapper.selectById(id);
    }


    @Override
    public boolean updateDept(Dept dept) {
        //直接调用持久层
        return this.deptMapper.updateById(dept) == 1 ;
    }

    @Override
    public boolean deleteDept(Integer id) {
        //判断此部门是否有用户
        Long count = this.userMapper.selectCount(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getDeptId, id)
        );
        if (count > 0) {
            throw new QkException("【错误】在用户表中还有此部门的记录，禁止删除！");
        }

        //直接调用持久层
        return this.deptMapper.deleteById(id) == 1;

    }

    @Override
    public List<Dept> findAll() {
        return this.deptMapper.selectList(Wrappers.emptyWrapper());
    }

}
