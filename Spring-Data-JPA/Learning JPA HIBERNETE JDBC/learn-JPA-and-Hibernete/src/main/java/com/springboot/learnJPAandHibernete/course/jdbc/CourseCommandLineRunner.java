package com.springboot.learnJPAandHibernete.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CourseJdbcRepository courseJdbcRepository;

    @Override
    public void run(String... args) throws Exception {
        courseJdbcRepository.insert();
        courseJdbcRepository.insert(new Course(2,"JPA","Aravind Sai"));
        courseJdbcRepository.insert(new Course(3,"Microservices","Aravind Sai"));
        courseJdbcRepository.insert(new Course(4,"Java developer","Aravind Sai"));

        System.out.println("courseJdbcRepository.findById(1) = " + courseJdbcRepository.findById(1));

        courseJdbcRepository.delete(1);

    }
}
