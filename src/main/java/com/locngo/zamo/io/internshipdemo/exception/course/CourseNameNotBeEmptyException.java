package com.locngo.zamo.io.internshipdemo.exception.course;

public class CourseNameNotBeEmptyException extends RuntimeException{
    public CourseNameNotBeEmptyException() {
        super("Sorry, course's name not be empty!");
    }
}
