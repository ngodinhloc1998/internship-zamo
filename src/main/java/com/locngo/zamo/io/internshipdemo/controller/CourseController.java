package com.locngo.zamo.io.internshipdemo.controller;

import com.locngo.zamo.io.internshipdemo.exception.course.InvalidInputException;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/apis/courses",method = RequestMethod.POST)
    public @ResponseBody
    Course createNewCourse(@RequestBody Course course){
        return courseService.createNewCourse(course);
    }

    @RequestMapping(value = "/apis/courses",method = RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourses(){
        return courseService.getCourses();
    }

    @RequestMapping(value = "/apis/courses/{id}", method = RequestMethod.GET)
    public @ResponseBody Course
    getCourseByName(@PathVariable("id") Long id){
        return courseService.getCourseById(id);
    }

}
