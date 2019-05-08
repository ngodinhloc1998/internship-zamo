package com.locngo.zamo.io.internshipdemo.exception.roleapplication;

public class RoleIsNullException extends RuntimeException {
    public RoleIsNullException() {
        super("Role can not be null!");
    }

}
