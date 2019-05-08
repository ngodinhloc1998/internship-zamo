package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class UsernameWasExistException extends RuntimeException{
    public UsernameWasExistException(String username) {
        super("Username '" + username + "' was exist!");
    }
}
