package com.itheima.controller;

import com.itheima.anno.Logger;
import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.entity.Dept;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Logger
    @PostMapping("/depts")
    public Result addDept(@RequestBody Dept dept) {

        log.info("添加部门，参数：部门名称：{}，状态：{}", dept.getName(), dept.getStatus());
        //直接调用业务层
        boolean b = this.deptService.addDept(dept);
        //返回
        return b ? Result.success() : Result.error("添加失败，请重试！");


    }

    @GetMapping("/depts")
    public Result deptList(
                String name,
                Integer status,
                @RequestParam(value = "page",defaultValue = "1") Integer page,
                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
            ){
        //传给业务层
        PageResult<Dept> pageResult = this.deptService.deptList(name,status,page,pageSize);
        //封装返回结果
        return Result.success(pageResult);
    }


    //3.根据ID查询部门
    @GetMapping("/depts/{id}")
    public Result findById(@PathVariable Integer id) {//路径参数，要使用@PathVariable注解
        //1.直接调用业务层
        Dept dept = this.deptService.findById(id);
        //2.返回
        return Result.success(dept);
    }


    //4.修改部门
    @Logger
    @PutMapping("/depts")
    public Result updateDept(@RequestBody Dept dept) {
        //1.直接调用业务层
        boolean b = this.deptService.updateDept(dept);
        //2.返回
        return b ? Result.success() : Result.error("修改失败，请重试！");
    }

    //5.删除部门
    @Logger
    @DeleteMapping("/depts/{id}")
    public Result deleteDept(@PathVariable Integer id) {
        //直接调用业务层
        boolean b = this.deptService.deleteDept(id);
        //返回
        return b ? Result.success() : Result.error("删除失败，请重试！");
    }

    @GetMapping("/depts/list")
    public Result deptList() {
        List<Dept> deptList = this.deptService.findAll();
        return Result.success(deptList);
    }
}
