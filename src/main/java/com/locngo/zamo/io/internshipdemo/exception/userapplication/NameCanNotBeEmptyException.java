package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class NameCanNotBeEmptyException extends RuntimeException{
    public NameCanNotBeEmptyException() {
        super("Sorry, name can not be empty!");
    }
}
