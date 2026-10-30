package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageBusinessDto;
import com.itheima.entity.Business;
import com.itheima.vo.OverviewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessMapper extends BaseMapper<Business> {

    Page<Business> selectByCondition(IPage<Business> page, PageBusinessDto businessDto);

    Business findDetailById(Integer id);

    Page<Business> pageBusinessPool(IPage<Business> page, PageBusinessDto poolDto);

    /**
     * 获取商机概览统计数据
     * @return
     */
    OverviewVO overview();
}