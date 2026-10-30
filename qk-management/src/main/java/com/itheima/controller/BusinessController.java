package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.dto.BusinessTrackDto;
import com.itheima.dto.PageBusinessDto;
import com.itheima.entity.Business;
import com.itheima.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businesses")
public class BusinessController {
    private final BusinessService businessService;

    //1.分页列表
    @GetMapping
    public Result pageBusiness(PageBusinessDto businessDto) {
        //1.调用业务层
        PageResult<Business> pageResult = businessService.pageBusiness(businessDto);
        //2.返回
        return Result.success(pageResult);
    }

    //2.添加商机
    @PostMapping
    public Result addBusiness(@RequestBody Business business) {
        boolean b = businessService.addBusiness(business);
        return b ? Result.success() : Result.error("添加商机失败，请重试！");
    }

    //3.分配商机
    @PutMapping("/assign/{businessId}/{userId}")
    public Result assignBusiness(@PathVariable Integer businessId, @PathVariable Integer userId) {
        boolean b = businessService.assignBusiness(businessId, userId);
        return b ? Result.success() : Result.error("分配商机失败，请重试！");
    }

    //4.踢回公海
    @PutMapping("/back/{id}")
    public Result backToPool(@PathVariable Integer id) {
        boolean b = businessService.backToPool(id);
        return b ? Result.success() : Result.error("踢回公海失败，请重试！");
    }

    //5.转客户处理
    @PostMapping("/toCustomer/{id}")
    public Result toCustomer(@PathVariable Integer id) {
        boolean b = businessService.toCustomer(id);
        return b ? Result.success() : Result.error("转客户失败，请重试！");
    }

    //6.根据ID查询商机
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Business business = businessService.findById(id);
        return Result.success(business);
    }

    //7.跟进商机
    @PutMapping
    public Result trackBusiness(@RequestBody BusinessTrackDto trackDto) {
        boolean b = businessService.trackBusiness(trackDto);
        return b ? Result.success() : Result.error("跟进商机失败，请重试！");
    }

    //8.公海池列表
    @GetMapping("/pool")
    public Result pageBusinessPool(PageBusinessDto poolDto) {
        PageResult<Business> pageResult = businessService.pageBusinessPool(poolDto);
        return Result.success(pageResult);
    }
}