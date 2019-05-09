package com.locngo.zamo.io.internshipdemo.service.course;

import com.locngo.zamo.io.internshipdemo.exception.usercourse.CanNotFoundUserCourseException;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.usercourse.UserCourse;
import com.locngo.zamo.io.internshipdemo.repository.course.CourseRepository;
import com.locngo.zamo.io.internshipdemo.repository.course.UserCourseRepository;
import com.locngo.zamo.io.internshipdemo.repository.roleapplication.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class UserCourseService {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    public UserCourse addNewCourse(UserApplication userApplication, Course course){
        UserCourse userCourse = new UserCourse();
        userCourse.setUserApplication(userApplication);
        userCourse.setCourse(course);
        return userCourseRepository.save(userCourse);
    }

    public UserCourse getUserCourse(String username,String courseName){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserCourse userCourse = (UserCourse)entityManager.createQuery(
                "SELECT uc FROM Course c JOIN c.userCourses uc JOIN uc.userApplication u" +
                        " WHERE c.name = :courseName AND u.username = :username"
        ).setParameter("courseName",courseName).setParameter("username",username).getSingleResult();
        if (userCourse == null){
            throw new CanNotFoundUserCourseException();
        }
        return userCourse;
    }
}
