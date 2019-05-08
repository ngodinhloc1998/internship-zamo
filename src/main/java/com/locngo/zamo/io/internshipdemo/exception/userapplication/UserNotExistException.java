package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super("This user does not exits !");
    }
}
