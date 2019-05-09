package com.locngo.zamo.io.internshipdemo.repository.course;

import com.locngo.zamo.io.internshipdemo.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    public Course findCourseByName(String name);
    public boolean existsCourseByName(String name);
}
