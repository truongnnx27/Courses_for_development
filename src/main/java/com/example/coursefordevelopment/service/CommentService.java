package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.UserCommentDto;
import com.example.coursefordevelopment.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentDto commentDto);

    Comment findCommentById(long id);

    boolean isCommentExist(long id);

    void deleteComment(long id);

    List<UserCommentDto> findCommentsByLessonId(long id);

    Comment putComment(long id, CommentDto commentDto);
}
