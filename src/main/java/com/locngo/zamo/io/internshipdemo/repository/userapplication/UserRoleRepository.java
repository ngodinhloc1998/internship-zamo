package com.locngo.zamo.io.internshipdemo.repository.userapplication;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
