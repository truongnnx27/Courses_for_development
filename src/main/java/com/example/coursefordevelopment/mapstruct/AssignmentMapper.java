package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.AssignmentDto;
import com.example.coursefordevelopment.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    AssignmentDto assignmentToAssignmentDto(Assignment assignment);

    Assignment assignmentDtoToAssignment(AssignmentDto assignmentDto);
}
