package com.locngo.zamo.io.internshipdemo.exception.roleapplication;

public class CanNotFoundRoleByNameException extends RuntimeException {
    public CanNotFoundRoleByNameException(String name) {
        super("Sorry, this role not exist in the system!");
    }
}
