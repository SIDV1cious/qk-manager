package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.PageResult;
import com.itheima.entity.Course;

import java.util.List;

/**
 * 课程业务接口
 */
public interface CourseService extends IService<Course> {

    /**
     * 条件分页查询课程
     *
     * @param name     课程名称
     * @param subject  课程学科
     * @param target   适用人群
     * @param page     当前页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    PageResult<Course> pageCourse(
            String name,
            Integer subject,
            Integer target,
            Integer page,
            Integer pageSize
    );

    /**
     * 根据学科查询课程
     *
     * @param subject 课程学科
     * @return 课程列表
     */
    List<Course> findBySubject(Integer subject);
}