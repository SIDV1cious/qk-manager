package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 活动实体类
 * 对应数据库表：activity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("activity")
public class Activity {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 渠道 (tinyint unsigned)
     */
    private Integer channel;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动类型 (tinyint unsigned)
     */
    private Integer type;

    /**
     * 折扣 (double)
     */
    private Double discount;

    /**
     * 代金券/优惠券 (int unsigned)
     */
    private Long voucher;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}