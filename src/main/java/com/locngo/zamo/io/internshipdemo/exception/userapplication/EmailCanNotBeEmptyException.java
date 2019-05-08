package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class EmailCanNotBeEmptyException extends RuntimeException {
    public EmailCanNotBeEmptyException() {
        super("Sorry, email can not be empty!");
    }
}
