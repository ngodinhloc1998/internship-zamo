package com.locngo.zamo.io.internshipdemo.repository;

import com.locngo.zamo.io.internshipdemo.model.RoleApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleApplicationRepository extends JpaRepository<RoleApplication,Long> {
    public RoleApplication findRoleByName(String name);
    public void deleteRoleByName(String name);
    public boolean existsRoleById(Long id);
    public boolean existsRoleApplicationByName(String name);
}
