package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.UserLoginDto;
import com.itheima.service.UserService;
import com.itheima.vo.UserLoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto loginDto) {
        UserLoginVo userLoginVo = userService.login(loginDto);
        return Result.success(userLoginVo);
    }
}