package com.locngo.zamo.io.internshipdemo.service.course;

import com.locngo.zamo.io.internshipdemo.exception.course.*;
import com.locngo.zamo.io.internshipdemo.exception.userapplication.CanNotFoundUserByUsernameException;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private boolean checkValidateCourseByName(Course course){
        if(course == null || course.getName() == null || course.getDuration() == null){
            throw new CourseFieldNotEnoughException();
        }
        if(course.getName().equals("")){
            throw new CourseNameNotBeEmptyException();
        }
        if(courseRepository.existsCourseByName(course.getName())){
            throw new CourseNameExistedException(course.getName());
        }
        return true;
    }

    @Autowired
    private CourseRepository courseRepository;

    public Course createNewCourse(Course course){
        if(checkValidateCourseByName(course)){
            return courseRepository.save(course);
        }
        return null;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByName(String name){
        if(!courseRepository.existsCourseByName(name)){
            throw new CanNotFoundCourseException(name);
        }
        return courseRepository.findCourseByName(name);
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new CanNotFoundCourseByIdException(id));
    }

}
