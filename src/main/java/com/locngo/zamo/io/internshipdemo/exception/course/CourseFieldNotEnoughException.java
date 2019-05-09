package com.locngo.zamo.io.internshipdemo.exception.course;

public class CourseFieldNotEnoughException extends RuntimeException {

    public CourseFieldNotEnoughException() {
        super("Sorry, please provide all field of course!");
    }
}
