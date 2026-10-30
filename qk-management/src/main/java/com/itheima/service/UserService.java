package com.itheima.service;

import com.itheima.common.PageResult;
import com.itheima.dto.PageUserDto;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;
import com.itheima.vo.UserLoginVo;

import java.util.List;

public interface UserService {
    //1.添加用户
    public boolean addUser(User user);
    //2.修改用户
    public boolean updateUser(User user);
    //3.id查询一条
    public User findById(Integer id);
    //4.分页查询
    public PageResult<User> findByPage(PageUserDto userDto);
    //5.删除
//    public boolean deleteById(Integer id);

    boolean deleteByIds(List<Integer> idList);
    //7.处理登陆
    UserLoginVo login(UserLoginDto loginDto);

    Object findUserByRoleLabel(String roleLabel);
}
