package com.locngo.zamo.io.internshipdemo.exception.course;

public class CourseNameExistedException extends RuntimeException {
    public CourseNameExistedException(String courseName) {
        super("Course name '" + courseName +"' existed!");
    }
}
