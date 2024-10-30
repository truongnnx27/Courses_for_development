package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.request.CommentInLectureReques;
import com.example.coursefordevelopment.dto.response.CommentReponse;
import com.example.coursefordevelopment.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentReponse addComment(CommentReponse commentReponse);
    CommentReponse updateComment(CommentReponse commentReponse);
    List<CommentInLectureReques> getCommentsInLecture(long lectureId);
    void deleteComment(long commentId);
}
