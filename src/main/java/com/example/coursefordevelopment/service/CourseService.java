package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CourseDto;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    private CourseDto convertToCourseDTO(Course course) {
        CourseDto courseDTO = new CourseDto();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setCategory(course.getCategory());
        courseDTO.setRating(course.getRating());
        courseDTO.setNumberOfRatings(course.getNumberOfRatings());
        courseDTO.setNumberOfStudents(course.getNumberOfStudents());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setLanguage(course.getLanguage());
        courseDTO.setUserId(course.getUser().getId());
        courseDTO.setCourseLevelId(course.getCourseLevel().getId());

        return courseDTO;
    }
}
