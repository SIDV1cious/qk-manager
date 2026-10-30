package com.itheima.dto;

import lombok.Data;

@Data
public class PageBusinessDto {
    /**
     * 商机ID
     */
    private Integer businessId;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 商机状态，1:待分配, 2:待跟进, 3:跟进中, 4:回收, 5:转客户
     */
    private Integer status;

    /**
     * 商机归属人姓名
     */
    private String assignName;

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