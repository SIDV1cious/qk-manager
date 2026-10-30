package com.itheima.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageClueDto;
import com.itheima.entity.Clue;
import com.itheima.vo.OverviewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueMapper extends BaseMapper<Clue> {

    Page<Clue> selectByCondition(IPage<Clue> objectPage, PageClueDto clueDto);

    Clue findDetailById(Integer id);

    Page<Clue> pageCluePool(Page<Clue> cluePage, PageClueDto poolDto);

    /**
     * 获取线索概览统计数据
     * @return
     */
    OverviewVO overview();
}
