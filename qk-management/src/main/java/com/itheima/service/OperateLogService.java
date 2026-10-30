package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.dto.PageOperateLogDto;
import com.itheima.entity.OperateLog;

public interface OperateLogService extends IService<OperateLog> {
    PageResult<OperateLog> pageOperateLog(PageOperateLogDto operateLogDto);
}