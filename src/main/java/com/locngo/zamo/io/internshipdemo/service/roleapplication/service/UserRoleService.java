package com.locngo.zamo.io.internshipdemo.service.roleapplication.service;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.UserRole;

public interface UserRoleService {
    public UserRole createNewUserRole(String username, String roleName);
}
