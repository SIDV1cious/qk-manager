package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程Mapper
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}