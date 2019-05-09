package com.locngo.zamo.io.internshipdemo.repository.course;

import com.locngo.zamo.io.internshipdemo.model.usercourse.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {
}
