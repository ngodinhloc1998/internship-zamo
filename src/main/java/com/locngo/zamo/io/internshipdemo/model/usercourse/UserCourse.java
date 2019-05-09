package com.locngo.zamo.io.internshipdemo.model.usercourse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_course")
@Entity
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")
    @Column(name = "started_date",insertable = false)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedDate;

    @Column(name = "is_completed")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isCompleted = false;

    @Column(name = "amount_completed")
    private Long amountCompleted = Long.valueOf(0);

    @Nullable
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_application")
    private UserApplication userApplication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_course")
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Long getAmountCompleted() {
        return amountCompleted;
    }

    public void setAmountCompleted(Long amountCompleted) {
        this.amountCompleted = amountCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserApplication getUserApplication() {
        return userApplication;
    }

    public void setUserApplication(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
