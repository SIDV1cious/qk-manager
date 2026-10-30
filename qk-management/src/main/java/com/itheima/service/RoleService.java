package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    PageResult<Role> selectByPage(String name, String label, Integer page, Integer pageSize);

    public Role selectRoleById(Integer id);

    List<Role> findAll();

    public boolean deleteRoleById(Integer id);
}
