package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.response.CommentInCourseResponse;
import com.example.coursefordevelopment.dto.response.CommentInLectureResponse;
import com.example.coursefordevelopment.dto.request.CommentRequest;

import java.util.List;

public interface CommentService {
    CommentRequest addComment(CommentRequest commentRequest);
    CommentRequest updateComment(CommentRequest commentRequest);
    List<CommentInLectureResponse> getCommentsInLecture(long lectureId);
    List<CommentInCourseResponse> getCommentInCourse(long courseId);
    void deleteComment(long commentId);
}
