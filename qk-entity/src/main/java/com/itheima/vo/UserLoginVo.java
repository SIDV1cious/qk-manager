package com.itheima.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

//封装返回值
@Data
public class UserLoginVo {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password; // 密码:用于后期密码比对,不返回前端
    @JsonIgnore
    private String salt; // 盐 : 用户后期用户密码加密比对,不返回前端
    private String name;
    private String image;
    private String roleLabel;
    private String token;
}