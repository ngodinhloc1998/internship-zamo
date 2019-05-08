package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class CanNotFoundUserByUsernameException extends RuntimeException {
    public CanNotFoundUserByUsernameException(String username) {
        super("Can not found '" + username + "' in the system!");
    }
}
