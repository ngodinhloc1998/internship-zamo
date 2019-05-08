package com.locngo.zamo.io.internshipdemo.exception.roleapplication;

public class RoleNameWasExistException extends RuntimeException {
    public RoleNameWasExistException(String name) {
        super("Sorry, role name '" + name +"' existed!");
    }
}