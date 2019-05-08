package com.locngo.zamo.io.internshipdemo.exception.jwt;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Sorry, you provide invalid token!");
    }
}
