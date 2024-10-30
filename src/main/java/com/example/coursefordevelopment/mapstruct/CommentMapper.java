package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.response.CommentReponse;
import com.example.coursefordevelopment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "idUserComment", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "lectureId", target = "lecture.id")
    @Mapping(source = "parentId", target = "comment.id")
    Comment commentRepsToComment(CommentReponse commentReponse);

    @Mapping(source = "user.id", target = "idUserComment")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "lecture.id", target = "lectureId")
    @Mapping(source = "comment.id", target = "parentId")
    CommentReponse commentToCommentReps(Comment comment);


}
