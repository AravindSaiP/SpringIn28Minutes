package com.learn_spring_boot.basic_restapi.course_controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {

    @RequestMapping("/courses")
    public List<Courses> retrieveAllCourses(){
        return Arrays.asList(
                new Courses(1,"Java Developer","Aravind Sai"),
                new Courses(2,"Microservices","Aravind Sai"),
                new Courses(3,"System Design","Aravind Sai"),
                new Courses(4,"LLD","Aravind Sai")
        );
    }
}
