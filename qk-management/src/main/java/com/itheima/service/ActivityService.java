package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.entity.Activity;

public interface ActivityService extends IService<Activity> {
    PageResult<Activity> listByPage(Integer channel, Integer type, Integer page, Integer pageSize);

}
