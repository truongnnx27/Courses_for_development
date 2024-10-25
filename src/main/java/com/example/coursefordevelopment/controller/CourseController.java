package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.request.CourseCreationRequest;
import com.example.coursefordevelopment.dto.response.CourseResponse;
import com.example.coursefordevelopment.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreationRequest courseCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseCreationRequest));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getAllCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getAllCourses(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(Map.of("message", "Course "+id+" deleted successfully"));
    }
}
