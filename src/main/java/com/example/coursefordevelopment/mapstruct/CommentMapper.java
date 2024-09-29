package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    Comment commentDtoToComment(CommentDto commentDto);
}
