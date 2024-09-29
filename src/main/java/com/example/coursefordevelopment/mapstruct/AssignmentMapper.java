package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.AssignmentDto;
import com.example.coursefordevelopment.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    @Mapping(source = "courseEntity.id", target = "courseId")
    @Mapping(source = "lesson.id", target = "lessonId")
    AssignmentDto assignmentToAssignmentDto(Assignment assignment);

    @Mapping(source = "courseId", target = "courseEntity.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    Assignment assignmentDtoToAssignment(AssignmentDto assignmentDto);
}
