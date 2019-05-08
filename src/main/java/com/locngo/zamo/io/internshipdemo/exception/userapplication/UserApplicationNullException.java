package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class UserApplicationNullException extends RuntimeException {
    public UserApplicationNullException() {
        super("User application can not be null");
    }
}
