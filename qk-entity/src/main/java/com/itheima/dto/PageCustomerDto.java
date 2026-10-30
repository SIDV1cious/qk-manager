package com.itheima.dto;

import lombok.Data;

@Data
public class PageCustomerDto {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 渠道来源，1:线上活动, 2:推广介绍
     */
    private Integer channel;

    /**
     * 意向学科
     */
    private Integer subject;

    /**
     * 分页查询的页码，如果未指定，默认为1
     */
    private Integer page = 1;

    /**
     * 分页查询的每页记录数，如果未指定，默认为10
     */
    private Integer pageSize = 10;
}