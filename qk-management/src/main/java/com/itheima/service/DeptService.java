package com.itheima.service;

import com.itheima.common.PageResult;
import com.itheima.entity.Dept;

import java.util.List;

public interface DeptService {
    boolean addDept(Dept dept);

    PageResult<Dept> deptList(String name, Integer status, Integer page, Integer pageSize);

    Dept findById(Integer id);

    boolean updateDept(Dept dept);

    boolean deleteDept(Integer id);

    List<Dept> findAll();
}
