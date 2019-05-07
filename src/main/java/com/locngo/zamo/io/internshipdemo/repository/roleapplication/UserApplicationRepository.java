package com.locngo.zamo.io.internshipdemo.repository.roleapplication;

import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication,Long> {

    public UserApplication findUserApplicationsByUsername(String username);
    public UserApplication findUserApplicationByEmail(String email);
    public List<UserApplication> findUserApplicationByName(String name);
    public boolean existsUserApplicationByUsername(String username);
    public boolean existsUserApplicationByEmail(String email);
    public void deleteUserApplicationByUsername(String username);
    public void deleteUserApplicationByEmail(String email);
}
