package com.locngo.zamo.io.internshipdemo.service;


import com.locngo.zamo.io.internshipdemo.model.RoleApplication;
import com.locngo.zamo.io.internshipdemo.repository.RoleApplicationRepository;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.RoleApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
@Qualifier("RoleApplicationServiceImpl")
public class RoleApplicationServiceImpl implements RoleApplicationService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private RoleApplicationRepository roleApplicationRepository;

    @Override
    public RoleApplication createNewRole(RoleApplication roleApplication) {
        if(roleApplication != null){
            if(roleApplicationRepository.findAll() != null || roleApplicationRepository.findAll().size() == 0){
                if(roleApplicationRepository.existsRoleByName(roleApplication.getName())){
                    throw new RuntimeException("This role was exist!");
                }
            }
            return roleApplicationRepository.save(roleApplication);
        }else{
            throw new RuntimeException("Invalid Input Role!");
        }
    }

    @Override
    public RoleApplication updateRole(RoleApplication roleApplication) {
        if(roleApplication != null && roleApplicationRepository.existsRoleByName(roleApplication.getName())){
            roleApplication.setId(roleApplicationRepository.findRoleByName(roleApplication.getName()).getId());
            return roleApplicationRepository.save(roleApplication);
        }else{
            throw new RuntimeException("Invalid Input Role!");
        }
    }

    @Override
    public RoleApplication getRole(Long id) {

        return roleApplicationRepository.findById(id).orElseThrow(()-> new RuntimeException("This user does not exist!"));
    }

    @Override
    public RoleApplication getRoleByName(String name) {
        return roleApplicationRepository.findRoleByName(name);
    }

    @Override
    public List<RoleApplication> getRoles() {
        return roleApplicationRepository.findAll();
    }
}
