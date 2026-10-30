package com.itheima.dto;

import com.itheima.entity.Clue;
import lombok.Data;

@Data
public class ClueTrackDto extends Clue {
    private String record; // 跟进记录
}