package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.CourseDto;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }
    // API để lấy chi tiết khóa học theo id
    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }
}
