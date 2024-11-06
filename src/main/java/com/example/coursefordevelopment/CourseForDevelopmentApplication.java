package com.example.coursefordevelopment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseForDevelopmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseForDevelopmentApplication.class, args);
    }

}
