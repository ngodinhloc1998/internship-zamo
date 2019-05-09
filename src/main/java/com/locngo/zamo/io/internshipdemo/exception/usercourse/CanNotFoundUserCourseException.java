package com.locngo.zamo.io.internshipdemo.exception.usercourse;

public class CanNotFoundUserCourseException extends RuntimeException{
    public CanNotFoundUserCourseException() {
        super("Opps, can not found this details user course");
    }
}