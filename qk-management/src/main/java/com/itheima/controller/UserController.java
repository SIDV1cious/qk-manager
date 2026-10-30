package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.dto.PageUserDto;
import com.itheima.entity.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    //1.分页列表
    @GetMapping
    public Result selectByPage(PageUserDto userDto){
        //1.调用业务层
        PageResult<User> byPage =
                this.userService.findByPage(userDto);
        //2.返回
        return Result.success(byPage);


    }

    //2.添加用户
    @PostMapping
    public Result addUser(@RequestBody User user) {
        //2.调用业务层
        boolean b = this.userService.addUser(user);
        return b ? Result.success() : Result.error("添加失败，请重试！");
    }

    //3.删除用户(含：批量删除)
    @DeleteMapping("/{ids}")
    public Result deleteByIds(@PathVariable("ids") List<Integer> idList) {
        //3.调用service删除
        boolean b = this.userService.deleteByIds(idList);
        return b ? Result.success() : Result.error("删除失败，请重试！");
    }

    //4.通过id查询一个用户
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        User user = this.userService.findById(id);
        return Result.success(user);
    }

    //5.修改
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        boolean b = this.userService.updateUser(user);
        return b ? Result.success() : Result.error("修改失败，请重试！");
    }


    @GetMapping("/role/{roleLabel}")
    public Result getUserByRoleLabel(@PathVariable String roleLabel) {
        return Result.success(this.userService.findUserByRoleLabel(roleLabel));
    }
}
