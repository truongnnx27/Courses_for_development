package coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.CourseDto;
import com.example.coursefordevelopment.entity.*;
import com.example.coursefordevelopment.mapstruct.CourseMapper;
import com.example.coursefordevelopment.repository.CourseRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper = CourseMapper.INSTANCE;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        User instructor = userRepository.findById(String.valueOf(courseDto.getInstructor())).orElseThrow(() -> new RuntimeException("Instructor not found"));
        Course course = courseMapper.courseDtoToCourse(courseDto);
        course.setInstructor(instructor); // Thiết lập quan hệ giữa Course và User
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
        return courseMapper.courseToCourseDto(course);
    }



    @Override
    public Page<CourseDto> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable).map(courseMapper::courseToCourseDto);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.courseToCourseDto(course);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Course course = courseMapper.courseDtoToCourse(courseDto);
        return courseMapper.courseToCourseDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.deleteById(id);
    }
}
