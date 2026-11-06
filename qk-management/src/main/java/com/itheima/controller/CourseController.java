package com.itheima.controller;

import com.itheima.common.PageResult;
import com.itheima.common.Result;
import com.itheima.entity.Course;
import com.itheima.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理
 */
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * 条件分页查询课程
     */
    @GetMapping
    public Result pageCourse(
            String name,
            Integer subject,
            Integer target,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResult<Course> pageResult =
                courseService.pageCourse(
                        name, subject, target, page, pageSize
                );

        return Result.success(pageResult);
    }

    /**
     * 根据ID删除课程
     */
    @DeleteMapping("/{id}")
    public Result deleteCourse(@PathVariable Integer id) {

        boolean result = courseService.removeById(id);

        return result
                ? Result.success()
                : Result.error("课程不存在，删除失败");
    }

    /**
     * 新增课程
     */
    @PostMapping
    public Result addCourse(@RequestBody Course course) {

        boolean result = courseService.save(course);

        return result
                ? Result.success()
                : Result.error("课程新增失败");
    }

    /**
     * 根据ID查询课程
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {

        Course course = courseService.getById(id);

        return course == null
                ? Result.error("课程不存在")
                : Result.success(course);
    }

    /**
     * 修改课程
     */
    @PutMapping
    public Result updateCourse(@RequestBody Course course) {

        if (course.getId() == null) {
            return Result.error("修改参数缺失，请检查后重试");
        }

        boolean result = courseService.updateById(course);

        return result
                ? Result.success()
                : Result.error("课程不存在，修改失败");
    }

    /**
     * 查询所有课程
     */
    @GetMapping("/list")
    public Result findAll() {

        List<Course> courseList = courseService.list();

        return Result.success(courseList);
    }

    /**
     * 根据学科查询课程
     */
    @GetMapping("/subject/{subject}")
    public Result findBySubject(
            @PathVariable Integer subject) {

        List<Course> courseList =
                courseService.findBySubject(subject);

        return Result.success(courseList);
    }
}