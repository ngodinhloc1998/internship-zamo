package com.locngo.zamo.io.internshipdemo.service.roleapplication.service;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;

import java.util.List;

public interface RoleApplicationService {
    public RoleApplication createNewRole(RoleApplication roleApplication);
    public RoleApplication updateRole(RoleApplication roleApplication);
    public RoleApplication getRole(Long id);
    public RoleApplication getRoleByName(String name);
    public List<RoleApplication> getRoles();
    public boolean existRoleApplicationByName(String name);
}
