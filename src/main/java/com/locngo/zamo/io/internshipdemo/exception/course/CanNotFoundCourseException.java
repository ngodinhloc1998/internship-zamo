package com.locngo.zamo.io.internshipdemo.exception.course;

public class CanNotFoundCourseException extends RuntimeException {
    public CanNotFoundCourseException(String courseName) {
        super("Can not found any course course which name is '" + courseName + "'");
    }
}
