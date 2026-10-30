package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageUserDto;
import com.itheima.entity.User;
import com.itheima.vo.UserLoginVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> selectByCondition(IPage<User> page, PageUserDto dto);

    UserLoginVo findByUsername(String username);

    List<User> selectByRoleLabel(String roleLabel);
}
