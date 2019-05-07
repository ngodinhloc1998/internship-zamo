package com.locngo.zamo.io.internshipdemo.model.roleapplication;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "user_role_application")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_application")
    private UserApplication userApplication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role_application")
    private RoleApplication roleApplication;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserApplication getUserApplication() {
        return userApplication;
    }

    public void setUserApplication(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleApplication getRoleApplication() {
        return roleApplication;
    }

    public void setRoleApplication(RoleApplication roleApplication) {
        this.roleApplication = roleApplication;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
