package com.locngo.zamo.io.internshipdemo.service.roleapplication.impl;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.UserRole;
import com.locngo.zamo.io.internshipdemo.repository.userapplication.UserRoleRepository;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.RoleApplicationService;
import com.locngo.zamo.io.internshipdemo.service.userapplication.service.UserApplicationService;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("UserRoleServiceImpl")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private RoleApplicationService roleApplicationService;

    @Override
    public UserRole createNewUserRole(String username, String roleName) {
        UserRole userRole = new UserRole();
        if(userApplicationService.existUserApplicationByUsername(username)
            && roleApplicationService.existRoleApplicationByName(roleName)){
            userRole.setUserApplication(userApplicationService.getUserByUserName(username));
            userRole.setRoleApplication(roleApplicationService.getRoleByName(roleName));
            return userRoleRepository.save(userRole);
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
