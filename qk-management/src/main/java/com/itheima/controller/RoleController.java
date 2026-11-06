package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.entity.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    //1.添加角色
    @PostMapping
    public Result addRole(@RequestBody Role role) {

        //调用业务层
        boolean save = roleService.save(role);
        return save ? Result.success() : Result.error("添加失败，请重试！");
    }

    //2.分页查询
    @GetMapping
    public Result selectByPage(
            String name,String label,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ){
        //调用业务层
        PageResult<Role> pageResult = roleService.selectByPage(name, label, page, pageSize);
        //返回
        return Result.success(pageResult);
    }

    //3.根据ID查询一条
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        //直接调用业务层
        Role role = roleService.selectRoleById(id);
        //返回
        return Result.success(role);
    }

    //4.修改
    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        //直接调用业务层
        boolean b = roleService.updateById(role);
        //返回
        return b ? Result.success() : Result.error("修改失败，请重试！");
    }

    //5.删除
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        return roleService.deleteRoleById(id) ?
                Result.success() : Result.error("删除失败，请重试！");
    }

    //6.查所有
    @GetMapping("/list") //路径："/roles/list"
    public Result findAll(){
        List<Role> roleList = roleService.findAll();
        return Result.success(roleList);
    }

}
