package com.locngo.zamo.io.internshipdemo.repository;

import com.locngo.zamo.io.internshipdemo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
