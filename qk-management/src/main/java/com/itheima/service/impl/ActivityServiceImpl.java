package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.entity.Activity;
import com.itheima.mapper.ActivityMapper;
import com.itheima.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Override
    public PageResult<Activity> listByPage(Integer channel, Integer type, Integer page, Integer pageSize) {
        Page<Activity> page1 = this.page(
                new Page<>(page, pageSize),
                Wrappers.lambdaQuery(Activity.class)
                        .eq(channel != null, Activity::getChannel, channel)
                        .eq(type != null, Activity::getType, type)
        );
        return new PageResult<>(page1.getTotal(),page1.getRecords());
    }
}
