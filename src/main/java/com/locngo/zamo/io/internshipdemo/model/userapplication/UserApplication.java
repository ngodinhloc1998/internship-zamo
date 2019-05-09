package com.locngo.zamo.io.internshipdemo.model.userapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.locngo.zamo.io.internshipdemo.model.roleapplication.UserRole;
import com.locngo.zamo.io.internshipdemo.model.usercourse.UserCourse;
import lombok.Data;
import org.apache.catalina.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user_application")
public class UserApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "userApplication")
    @MapsId("id")
    @JsonIgnore
    private Set<UserRole> userRole;

    @JsonIgnore
    @OneToMany(mappedBy = "userApplication")
    @MapsId("id")
    private Set<UserCourse> userCourses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<UserRole> getUserRole() {
        if(this.userRole == null || this.userRole.size() == 0){
            userRole = new HashSet<UserRole>();
        }
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Set<UserCourse> getUserCourses() {
        if(userCourses == null || this.userCourses.size() == 0){
            userCourses = new HashSet<UserCourse>();
        }
        return userCourses;
    }

    public void setUserCourses(Set<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }
}
