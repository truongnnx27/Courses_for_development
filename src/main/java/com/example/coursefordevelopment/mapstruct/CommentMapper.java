package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "comment.id", target = "commentId")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    @Mapping(source = "commentId", target = "comment.id")
    Comment commentDtoToComment(CommentDto commentDto);

    List<CommentDto> listCommentToListCommentDto(List<Comment> listComment);

    List<CommentDto> listCommentDtoListComment(List<CommentDto> listCommentDto);
}
