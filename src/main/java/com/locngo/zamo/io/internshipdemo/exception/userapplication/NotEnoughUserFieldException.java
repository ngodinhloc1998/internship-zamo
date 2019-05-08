package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class NotEnoughUserFieldException extends RuntimeException {
    public NotEnoughUserFieldException() {
        super("Sorry, please fill enough field for construct user!");
    }
}
