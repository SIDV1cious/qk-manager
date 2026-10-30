package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.ClueMapper;
import com.itheima.service.ClueService;
import com.itheima.service.ReportService;
import com.itheima.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ClueMapper clueMapper;
    private final BusinessMapper businessMapper;
    @Override
    public OverviewVO overview() {

        OverviewVO clueOverview = clueMapper.overview();
        OverviewVO businessOverview = businessMapper.overview();
        BeanUtil.copyProperties(businessOverview,clueOverview, CopyOptions.create().ignoreNullValue());


        return clueOverview;
    }
}
