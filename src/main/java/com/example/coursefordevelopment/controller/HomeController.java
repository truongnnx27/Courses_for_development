package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.response.*;
import com.example.coursefordevelopment.service.CategoryService;
import com.example.coursefordevelopment.service.CourseService;
import com.example.coursefordevelopment.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HomeController {

    CourseService courseService;
    CategoryService categoryService;
    UserService userService;

    @GetMapping("/getNewCourse")
    public ResponseEntity<List<CourseResponse>> getNewCourse() {
        return ResponseEntity.ok(courseService.getNewCourses());
    }

    @GetMapping("/getCateNumberUser")
    public ResponseEntity<List<CategoryResponse>> getCateNumberUser() {
        return ResponseEntity.ok(categoryService.getCategoriesNumberUser());
    }

    @GetMapping("/getCoursBestSale")
    public ResponseEntity<List<CourseBestSaleResponse>> getAllUsers() {
        return ResponseEntity.ok(courseService.getCourseBestSale());
    }

    @GetMapping("/getTopIntructor")
    public ResponseEntity<List<TopIntructorResponse>> getTopIntructor() {
        return ResponseEntity.ok(userService.topIntructor());
    }
}