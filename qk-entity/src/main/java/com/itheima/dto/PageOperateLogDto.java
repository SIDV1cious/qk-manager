package com.itheima.dto;

import lombok.Data;

@Data
public class PageOperateLogDto {
    private String operateUserName;
    private String className;
    private String methodName;
    private Integer page = 1;
    private Integer pageSize = 10;
}
