package com.itheima.dto;

import com.itheima.entity.Business;
import lombok.Data;

import java.util.List;

@Data
public class BusinessTrackDto extends Business {
    private Integer trackStatus; // 跟进状态, 1:接通, 2:拒绝, 3:无人接听
    private List<String> keyItems; // 沟通重点
    private String record; // 沟通纪要
}