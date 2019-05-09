package com.locngo.zamo.io.internshipdemo.exception.course;

public class CanNotFoundCourseByIdException extends RuntimeException {
    public CanNotFoundCourseByIdException(Long id) {
        super("Can not found course which id is " + id);
    }
}
