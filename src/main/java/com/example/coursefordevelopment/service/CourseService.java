package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CourseDto;
import org.springframework.data.domain.Page;

public interface CourseService {
    Page<CourseDto> getAllCourses(int page, int size);
    CourseDto getCourseById(Long id);
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    void deleteCourse(Long id);
}
