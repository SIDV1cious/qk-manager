package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.dto.PageOperateLogDto;
import com.itheima.entity.OperateLog;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.service.OperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {
    private final OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> pageOperateLog(PageOperateLogDto operateLogDto) {
        //1.调用持久层
        Page<OperateLog> page = operateLogMapper.selectByCondition(
                new Page<>(operateLogDto.getPage(), operateLogDto.getPageSize()), operateLogDto);

        //2.创建一个PageResult
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}