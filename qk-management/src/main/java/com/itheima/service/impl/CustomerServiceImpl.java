package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.dto.PageCustomerDto;
import com.itheima.entity.Customer;
import com.itheima.mapper.CustomerMapper;
import com.itheima.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    private final CustomerMapper customerMapper;

    @Override
    public PageResult<Customer> pageCustomer(PageCustomerDto customerDto) {
        //1.调用持久层
        Page<Customer> page = customerMapper.selectByCondition(
                new Page<>(customerDto.getPage(), customerDto.getPageSize()), customerDto);

        //2.创建一个PageResult
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}