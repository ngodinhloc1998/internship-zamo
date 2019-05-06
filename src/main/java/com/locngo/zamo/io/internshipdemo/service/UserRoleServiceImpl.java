package com.locngo.zamo.io.internshipdemo.service;

import com.locngo.zamo.io.internshipdemo.model.UserRole;
import com.locngo.zamo.io.internshipdemo.repository.RoleApplicationRepository;
import com.locngo.zamo.io.internshipdemo.repository.UserApplicationRepository;
import com.locngo.zamo.io.internshipdemo.repository.UserRoleRepository;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.RoleApplicationService;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Qualifier("UserRoleServiceImpl")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private RoleApplicationService roleApplicationService;

    @Override
    public UserRole createNewUserRole(String username, String roleName) {
        UserRole userRole = new UserRole();
        if(userApplicationRepository.existsUserApplicationByUsername(username)
            && roleApplicationService.existRoleApplicationByName(roleName)){

            userRole.setUserApplication(userApplicationRepository.findUserApplicationsByUsername(username));
            userRole.setRoleApplication(roleApplicationService.getRoleByName(roleName));
            return userRoleRepository.save(userRole);
        }else{
            throw new RuntimeException("Can't create instance UserRole!");
        }
    }
}
