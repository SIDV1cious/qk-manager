package com.itheima.dto;

import lombok.Data;

@Data
public class CluePoolDto {
    private Integer clueId;
    private String phone;
    private Integer channel;
    private Integer page = 1;
    private Integer pageSize = 10;
}