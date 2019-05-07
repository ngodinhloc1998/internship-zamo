package com.locngo.zamo.io.internshipdemo.repository.userapplication;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleApplicationRepository extends JpaRepository<RoleApplication,Long> {
    public RoleApplication findRoleByName(String name);
    public void deleteRoleByName(String name);
    public boolean existsRoleById(Long id);
    public boolean existsRoleApplicationByName(String name);
}
