package com.itheima.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.PageResult;
import com.itheima.dto.PageUserDto;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;
import com.itheima.exception.QkException;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        //1.验证：用户名、手机号、邮箱：唯一
        //略

        //2.设置密码：
        //1).为此用户生成一个10位的字符串(盐值)
        String salt = RandomUtil.randomString(10);
        //2).用此盐值生成用户密码：(用户名 + “123” + 盐值) ->整体MD5加密
        String pwd = DigestUtil.md5Hex(user.getUsername() + "123" + salt);
        //3).设置到对象中
        user.setSalt(salt);
        user.setPassword(pwd);

        //调用持久层
        int row = this.userMapper.insert(user);
        return row == 1;
    }

    @Override
    public boolean updateUser(User user) {
        int row = this.userMapper.updateById(user);
        return row == 1;
    }

    @Override
    public User findById(Integer id) {
        return this.userMapper.selectById(id);
    }
    @Override
    public PageResult<User> findByPage(PageUserDto userDto) {
        //调用持久层，查询结果集
        Page<User> page = new Page(userDto.getPage(), userDto.getPageSize());
        Page<User> pageUser = this.userMapper.selectByCondition(page,userDto);

        return new PageResult<>(pageUser.getTotal(),pageUser.getRecords());
    }

    @Override
    public boolean deleteByIds(List<Integer> idList) {
        int i = this.userMapper.deleteByIds(idList);
        return i == idList.size();
    }

    @Override
    public UserLoginVo login(UserLoginDto loginDto) {
        // 1. 校验登录参数是否为空
        if (loginDto == null || StrUtil.isBlank(loginDto.getUsername()) || StrUtil.isBlank(loginDto.getPassword())) {
            throw new QkException("用户名和密码不能为空");
        }

        // 2. 调用数据访问层根据用户名查询用户信息
        UserLoginVo userLoginVo = userMapper.findByUsername(loginDto.getUsername());
        if (userLoginVo == null) {
            throw new QkException("用户名错误,请重新输入");
        }
        // 3. 使用数据库返回的 盐 + 用户输入的密码 进行MD5加密得到密文
        String md5Hex = DigestUtil.md5Hex(loginDto.getPassword() + userLoginVo.getSalt());
        // 4. 比较数据库中存储的密码和上一步加密出来的密文是否相同
        // 4.1 如果不同登录失败
        if (!StrUtil.equals(userLoginVo.getPassword(), md5Hex)) {
//        if(userLoginVo.getPassword()!= null && !userLoginVo.getPassword().equals(md5Hex)){
            throw new QkException("密码错误,请重新输入");
        }
        // 4.2 如果相同登录成功
        // 基于用户信息生成令牌
        Map<String,Object> payload = new HashMap<>();
        payload.put("id",userLoginVo.getId());
        payload.put("username",userLoginVo.getUsername());
        // 生成token令牌
        String token = JwtUtil.generateToken(payload);
        // 将令牌设置到响应实体对象中
        userLoginVo.setToken(token);

        return userLoginVo;
    }

    @Override
    public Object findUserByRoleLabel(String roleLabel) {
        List<User> userList = this.userMapper.selectByRoleLabel(roleLabel);
        return userList;
    }

}
