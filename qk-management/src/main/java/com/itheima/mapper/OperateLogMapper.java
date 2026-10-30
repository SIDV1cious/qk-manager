package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageOperateLogDto;
import com.itheima.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志管理Mapper
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
    Page<OperateLog> selectByCondition(IPage<OperateLog> page, PageOperateLogDto operateLogDto);
}
