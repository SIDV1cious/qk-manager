package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.dto.PageCustomerDto;
import com.itheima.entity.Customer;
import com.itheima.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    //1.分页列表
    @GetMapping
    public Result pageCustomer(PageCustomerDto customerDto) {
        //1.调用业务层
        PageResult<Customer> pageResult = customerService.pageCustomer(customerDto);
        //2.返回
        return Result.success(pageResult);
    }
}