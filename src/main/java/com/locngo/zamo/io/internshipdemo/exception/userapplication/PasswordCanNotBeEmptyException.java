package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class PasswordCanNotBeEmptyException extends RuntimeException {
    public PasswordCanNotBeEmptyException() {
        super("Sorry, password can not be empty");
    }
}
