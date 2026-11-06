package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.PageResult;
import com.itheima.entity.Course;
import com.itheima.mapper.CourseMapper;
import com.itheima.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程业务实现类
 */
@Service
public class CourseServiceImpl
        extends ServiceImpl<CourseMapper, Course>
        implements CourseService {

    /**
     * 条件分页查询课程
     */
    @Override
    public PageResult<Course> pageCourse(
            String name,
            Integer subject,
            Integer target,
            Integer page,
            Integer pageSize) {

        // 构建查询条件
        var wrapper = Wrappers.<Course>lambdaQuery()
                .like(name != null && !name.isBlank(),
                        Course::getName, name)
                .eq(subject != null,
                        Course::getSubject, subject)
                .eq(target != null,
                        Course::getTarget, target);

        // 执行分页查询
        Page<Course> pageInfo =
                this.page(new Page<>(page, pageSize), wrapper);

        // 封装分页结果
        return new PageResult<>(
                pageInfo.getTotal(),
                pageInfo.getRecords()
        );
    }

    /**
     * 根据学科查询课程
     */
    @Override
    public List<Course> findBySubject(Integer subject) {

        return this.list(
                Wrappers.<Course>lambdaQuery()
                        .eq(Course::getSubject, subject)
        );
    }
}