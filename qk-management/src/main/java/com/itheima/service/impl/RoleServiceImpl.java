package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.entity.Role;
import com.itheima.entity.User;
import com.itheima.exception.QkException;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {
    @Autowired
    private UserMapper userMapper;

    //分页查询
    @Override
    public PageResult<Role> selectByPage(String name, String label, Integer page, Integer pageSize) {
        Page<Role> page1 =this.page(
                new Page<>(page, pageSize),
                Wrappers.lambdaQuery(Role.class)
                        .like(StringUtils.isNotBlank(name), Role::getName, name)
                        .like(StringUtils.isNotBlank(label), Role::getLabel, label)
        );
        return new PageResult<>(page1.getTotal(), page1.getRecords());
    }

    @Override
    public Role selectRoleById(Integer id) {
        //1.验证参数
        if (id == null) {
            throw new QkException("【错误】角色数据不存在，请重试，或联系系统管理员！");
        }
        //2.执行查询
        Role role = this.getById(id);
        return role;
    }

    @Override
    public List<Role> findAll() {
        return this.list();
    }

    @Override
    public boolean deleteRoleById(Integer id) {
        //1.验证是否此角色是否有用户
        Long count = this.userMapper.selectCount(
                                Wrappers.lambdaQuery(User.class)
                                        .eq(User::getRoleId, id)
                        );
        if (count > 0) {
            throw new QkException("【错误】有用户被赋予此角色，不能删除！");
        }
        //2.执行删除
        return this.removeById(id);
    }
}
