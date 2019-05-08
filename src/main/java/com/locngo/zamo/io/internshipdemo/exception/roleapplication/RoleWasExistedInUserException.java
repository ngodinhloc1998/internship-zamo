package com.locngo.zamo.io.internshipdemo.exception.roleapplication;

public class RoleWasExistedInUserException extends RuntimeException {
    public RoleWasExistedInUserException(String username,String roleName) {
        super("Role '" + roleName + "' was contained by this user '" + username + "' !");
    }
}
