package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.dto.PageOperateLogDto;
import com.itheima.entity.OperateLog;
import com.itheima.service.OperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operateLogs")
public class OperateLogController {
    private final OperateLogService operateLogService;

    //1.分页列表
    @GetMapping
    public Result pageOperateLog(PageOperateLogDto operateLogDto) {
        //1.调用业务层
        PageResult<OperateLog> pageResult = operateLogService.pageOperateLog(operateLogDto);
        //2.返回
        return Result.success(pageResult);
    }
}