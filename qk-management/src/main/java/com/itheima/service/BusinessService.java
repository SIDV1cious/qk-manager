package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.dto.BusinessTrackDto;
import com.itheima.dto.PageBusinessDto;
import com.itheima.entity.Business;

public interface BusinessService extends IService<Business> {

    PageResult<Business> pageBusiness(PageBusinessDto businessDto);

    boolean addBusiness(Business business);

    boolean assignBusiness(Integer businessId, Integer userId);

    boolean backToPool(Integer id);

    boolean toCustomer(Integer id);

    Business findById(Integer id);

    boolean trackBusiness(BusinessTrackDto trackDto);

    PageResult<Business> pageBusinessPool(PageBusinessDto poolDto);
}