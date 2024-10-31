package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.request.CourseCreationRequest;
import com.example.coursefordevelopment.dto.response.CourseBestSaleResponse;
import com.example.coursefordevelopment.dto.response.CourseResponse;
import com.example.coursefordevelopment.entity.*;
import com.example.coursefordevelopment.mapstruct.CourseMapper;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.CourseService;
import com.example.coursefordevelopment.service.EmailService;
import com.example.coursefordevelopment.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ImageService imageService;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, UserRepository userRepository, EmailService emailService, ImageService imageService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.imageService = imageService;
    }

    @Override
    public CourseResponse createCourse(CourseCreationRequest courseCreationRequest) {
        Course course = courseMapper.toCourse(courseCreationRequest);
        course.setInstructor(userRepository.findById(courseCreationRequest.getInstructor())
                .orElseThrow(() -> new RuntimeException("Instructor not found"))); // Thiết lập quan hệ giữa Course và User
        // Thiết lập quan hệ trước khi lưu
        if (course.getSections() != null) {
            for (Section section : course.getSections()) {
                section.setCourse(course); // Thiết lập quan hệ giữa Section và Course
                if (section.getLectures() != null) {
                    for (Lecture lecture : section.getLectures()) {
                        lecture.setSection(section); // Thiết lập quan hệ giữa Lecture và Section
                        if (lecture.getVideos() != null) {
                            for (Video video : lecture.getVideos()) {
                                video.setLecture(lecture); // Thiết lập quan hệ giữa Video và Lecture
                            }
                        }
                        if (lecture.getQuiz() != null) {
                            lecture.getQuiz().setLecture(lecture); // Thiết lập quan hệ giữa Quiz và Lecture
                            if (lecture.getQuiz().getQuestions() != null) {
                                for (Question question : lecture.getQuiz().getQuestions()) {
                                    question.setQuiz(lecture.getQuiz()); // Thiết lập quan hệ giữa Question và Quiz
                                    if (question.getOptions() != null) {
                                        for (Option option : question.getOptions()) {
                                            option.setQuestion(question); // Thiết lập quan hệ giữa Option và Question
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Lưu toàn bộ thực thể cùng lúc với cascade
        course = courseRepository.save(course);

        // Trả về CourseDto sau khi đã lưu
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable).map(courseMapper::toCourseResponse);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseCreationRequest courseCreationRequest) {
        courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Course course = courseMapper.toCourse(courseCreationRequest);
        return courseMapper.toCourseResponse(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        emailService.sendEmailDeleteCourse(id);
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> getNewCourses() {
        Pageable pageable = PageRequest.of(0, 6);
        List<Course> courses = courseRepository.find6NewCourse(pageable);
        courses.forEach( course -> {
            try {
                String base64Image = imageService.base64Image(course.getCoverImage(), "src/main/resources/static/images/");
                course.setCoverImage(base64Image);
            } catch (IOException e) {
                course.setCoverImage("");
            }
        });
        return courseMapper.toCourseResponses(courses);
    }

    @Override
    public List<CourseBestSaleResponse> getCourseBestSale() {
        List<Object[]> results = courseRepository.findCourseBestSale();
        List<CourseBestSaleResponse> courseBestSaleResponses = results.stream()
                .map(result -> new CourseBestSaleResponse(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        (Long) result[3],
                        (String) result[4],
                        (String) result[5],
                        (Long) result[0]
                )).collect(Collectors.toList());
        courseBestSaleResponses.forEach(courseBestSaleResponse -> {
            courseBestSaleResponse.setNumberSection(numberSection(courseBestSaleResponse.getId()));
            try {
                courseBestSaleResponse.setCoverImage(imageService.base64Image(courseBestSaleResponse.getCoverImage(), "src/main/resources/static/images/"));
            } catch (IOException e) {
                courseBestSaleResponse.setCoverImage("");
            }

        });
        return courseBestSaleResponses;
    }

    private Long numberSection(Long courseId){
        return Long.valueOf(getCourseById(courseId).getSections().size());
    }

}
