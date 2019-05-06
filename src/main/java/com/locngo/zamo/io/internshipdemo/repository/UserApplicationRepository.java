package com.locngo.zamo.io.internshipdemo.repository;

import com.locngo.zamo.io.internshipdemo.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserApplicationRepository extends JpaRepository<UserApplication,Long> {

    public UserApplication findUserApplicationsByUsername(String username);
    public UserApplication findUserApplicationByEmail(String email);
    public List<UserApplication> findUserApplicationByName(String name);
    public boolean existsUserApplicationByUsername(String username);
    public boolean existsUserApplicationByEmail(String email);
    public void deleteUserApplicationByUsername(String username);
    public void deleteUserApplicationByEmail(String email);
}
