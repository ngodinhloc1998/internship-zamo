package com.locngo.zamo.io.internshipdemo.exception.course;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Something went wrong, please check the input");
    }
}
