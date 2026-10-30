package com.itheima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    封装："用户列表"的请求参数:6个参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUserDto {
    private String name;
    private Integer status;
    private String phone;
    private Integer deptId;
    private Integer page = 1;
    private Integer pageSize = 10;

}
