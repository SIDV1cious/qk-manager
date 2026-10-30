package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.dto.PageCustomerDto;
import com.itheima.entity.Customer;

public interface CustomerService extends IService<Customer> {
    PageResult<Customer> pageCustomer(PageCustomerDto customerDto);
}
