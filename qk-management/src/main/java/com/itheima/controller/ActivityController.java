package com.itheima.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.entity.Activity;
import com.itheima.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("/activities")
    public Result addActivity(@RequestBody Activity activity) {
        boolean save = this.activityService.save(activity);

        return save ? Result.success() : Result.error("添加失败，请重试！");
    }

    @GetMapping("/activities")
    public Result listByPage(
            Integer channel,
            Integer type,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageResult<Activity> pageResult = this.activityService.listByPage(channel, type, page, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/activities/{id}")
    public Result findById(@PathVariable Integer id) {
        return Result.success(this.activityService.getById(id));
    }

    @PutMapping("/activities")
    public Result updateActivity(@RequestBody Activity activity) {
        boolean b = this.activityService.updateById(activity);
        return b ? Result.success() : Result.error("修改错误，请重试！");
    }

    @DeleteMapping("/activities/{id}")
    public Result deleteById(@PathVariable Integer id) {
        boolean b = this.activityService.removeById(id);
        return b ? Result.success() : Result.error("删除失败，请重试！");
    }

    @GetMapping("/activities/type/{type}")
    public Result findByTypeId(@PathVariable Integer type) {
        List<Activity> list = this.activityService.list(
                                        Wrappers.lambdaQuery(Activity.class)
                                                .eq(Activity::getType, type)
                                );
        return Result.success(list);
    }
}
