package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.SubmissionDto;
import com.example.coursefordevelopment.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubmissionMapper {

    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);

    @Mapping(source = "assignment.id", target = "assignmentId")
    @Mapping(source = "user.id", target = "userId")
    SubmissionDto submissionToSubmissionDto(Submission submission);

    @Mapping(source = "assignmentId", target = "assignment.id")
    @Mapping(source = "userId", target = "user.id")
    Submission submissionDtoToSubmission(SubmissionDto submissionDto);
}
