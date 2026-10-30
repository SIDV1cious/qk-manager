package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.dto.ClueTrackDto;
import com.itheima.dto.MarkFalseClueDto;
import com.itheima.dto.PageClueDto;
import com.itheima.entity.Clue;

public interface ClueService extends IService<Clue> {
    PageResult<Clue> pageClue(PageClueDto clueDto);

    boolean assignClue(Integer clueId, Integer userId);

    Clue findById(Integer id);

    boolean trackClue(ClueTrackDto trackDto);

    boolean toBusiness(Integer clueId);

    boolean markClueFalse(Integer id, MarkFalseClueDto markFalseClueDto);

    /**
     * 分页条件查询线索池列表
     * @param poolDto
     * @return
     */
    PageResult<Clue> pageCluePool(PageClueDto poolDto);
}
