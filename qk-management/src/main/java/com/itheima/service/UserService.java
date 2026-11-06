package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.dto.PageUserDto;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;
import com.itheima.vo.UserLoginVo;

import java.util.List;

public interface UserService extends IService<User> {

    //1.添加用户
    boolean addUser(User user);

    //2.分页查询
    PageResult<User> findByPage(PageUserDto userDto);

    //3.批量删除
    boolean deleteByIds(List<Integer> idList);

    //4.处理登录
    UserLoginVo login(UserLoginDto loginDto);

    //5.根据角色查询用户
    List<User> findUserByRoleLabel(String roleLabel);

}