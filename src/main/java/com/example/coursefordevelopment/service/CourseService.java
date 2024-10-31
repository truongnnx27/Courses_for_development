package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.request.CourseCreationRequest;
import com.example.coursefordevelopment.dto.response.CourseBestSaleResponse;
import com.example.coursefordevelopment.dto.response.CourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    Page<CourseResponse> getAllCourses(int page, int size);
    CourseResponse getCourseById(Long id);
    CourseResponse createCourse(CourseCreationRequest courseCreationRequest);
    CourseResponse updateCourse(Long id, CourseCreationRequest courseCreationRequest);
    void deleteCourse(Long id);
    List<CourseResponse> getNewCourses();
    List<CourseBestSaleResponse> getCourseBestSale();
}