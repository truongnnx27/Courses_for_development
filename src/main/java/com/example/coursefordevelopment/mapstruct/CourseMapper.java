package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.request.CourseCreationRequest;
import com.example.coursefordevelopment.dto.response.CourseResponse;
import com.example.coursefordevelopment.dto.response.UserResponse;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class CourseMapper {
    @Autowired
    private UserRepository userRepository;

    public static final CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "instructor", target = "instructor", qualifiedByName = "stringToUser")
    public abstract Course toCourse(CourseCreationRequest courseDto);

    @Mapping(source = "instructor", target = "instructor", qualifiedByName = "userToUserResponse")
    public abstract CourseResponse toCourseResponse(Course course);

    @Named("stringToUser")
    User stringToUser(String instructor) {
        return userRepository.findById(instructor).orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    @Named("userToUserResponse")
    UserResponse userToUserResponse(User user) {
        return UserMapper.INSTANCE.toUserResponse(user);
    }
}
