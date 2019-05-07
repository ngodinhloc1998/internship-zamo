package com.locngo.zamo.io.internshipdemo.model.usersecurity;

import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.service.userapplication.service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class MyUserPrinciple implements UserDetailsService {


    @Autowired
    @Qualifier("UserApplicationServiceImpl")
    private UserApplicationService userApplicationService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserApplication userApplication = userApplicationService.getUserByUserName(username);
        if(userApplication == null){
            throw new RuntimeException("This username can't not found");
        }

        return User.withUsername(userApplication.getUsername())
                .password(userApplication.getPassword())
                .authorities(userApplicationService.getGrantedAuthorityByUsername(userApplication.getUsername()))
                .credentialsExpired(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
