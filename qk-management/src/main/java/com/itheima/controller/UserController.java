
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

    // 1. 分页查询用户
    @GetMapping
    public Result selectByPage(PageUserDto userDto) {
        PageResult<User> byPage =
                this.userService.findByPage(userDto);

        return Result.success(byPage);
    }

    // 2. 添加用户
    @PostMapping
    public Result addUser(@RequestBody User user) {
        boolean b = this.userService.addUser(user);

        return b ? Result.success() : Result.error("添加失败，请重试！");
    }

    // 3. 删除用户（支持批量删除）
    @DeleteMapping("/{ids}")
    public Result deleteByIds(@PathVariable("ids") List<Integer> idList) {
        boolean b = this.userService.deleteByIds(idList);

        return b ? Result.success() : Result.error("删除失败，请重试！");
    }

    // 4. 根据 ID 查询用户
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        User user = this.userService.getById(id);

        return Result.success(user);
    }

    // 5. 修改用户
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        boolean b = this.userService.updateById(user);

        return b ? Result.success() : Result.error("修改失败，请重试！");
    }

    // 6. 根据角色标签查询用户
    @GetMapping("/role/{roleLabel}")
    public Result getUserByRoleLabel(@PathVariable String roleLabel) {
        return Result.success(
                this.userService.findUserByRoleLabel(roleLabel)
        );
    }
}
