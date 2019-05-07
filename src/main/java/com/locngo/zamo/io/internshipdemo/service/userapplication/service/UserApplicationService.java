package com.locngo.zamo.io.internshipdemo.service.userapplication.service;

import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserApplicationService{
    public UserApplication createNewUser(UserApplication userApplication);
    public UserApplication updateUser(UserApplication userApplication);
    public UserApplication addNewRole(String username, String roleName);
    public UserApplication getUser(Long id);
    public UserApplication getUserByUserName(String username);
    public List<UserApplication> getUsers();
    public String signin(String username,String password);
    public String signup(UserApplication userApplication);
    public void signout(String username);
    public List<RoleApplication> getRolesByUsername(String username);
    public void deleteUser(Long id);
    public void deleteUserByUsername(String username);
    public String refresh(String username);
    public String whoami(String token);
    public boolean existUserApplicationByUsername(String username);
    public List<String> getNamesRoleAppicationByUsername(String username);
    public List<SimpleGrantedAuthority> getGrantedAuthorityByUsername(String username);
}
