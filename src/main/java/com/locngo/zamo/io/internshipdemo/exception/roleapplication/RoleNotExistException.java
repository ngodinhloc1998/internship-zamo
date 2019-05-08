package com.locngo.zamo.io.internshipdemo.exception.roleapplication;

public class RoleNotExistException extends RuntimeException {
    public RoleNotExistException(String name) {
        super("Role '" +name+ "'not exist!");
    }
}
