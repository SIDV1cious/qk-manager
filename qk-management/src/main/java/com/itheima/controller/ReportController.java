package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.service.ReportService;
import com.itheima.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/report")
@RestController
@RequiredArgsConstructor
public class ReportController {
 private final ReportService reportService;
 @GetMapping ("/overview")
public Result overview() {
    // 调用业务层获取概览统计数据
    OverviewVO overview = reportService.overview();
    return Result.success(overview);
}
}
