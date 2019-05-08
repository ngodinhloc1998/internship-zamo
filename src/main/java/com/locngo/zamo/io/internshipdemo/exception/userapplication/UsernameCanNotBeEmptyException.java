package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class UsernameCanNotBeEmptyException extends RuntimeException {
    public UsernameCanNotBeEmptyException() {
        super("Sorry, username cant not be empty!");
    }
}
