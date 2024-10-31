package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.response.CommentInCourseResponse;
import com.example.coursefordevelopment.dto.response.CommentInLectureResponse;
import com.example.coursefordevelopment.dto.request.CommentRequest;
import com.example.coursefordevelopment.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentController {
    CommentService commentService;

    //Truy xuất comment theo lecture
    @GetMapping("/getCommentLecture/{id}")
    public ResponseEntity<List<CommentInLectureResponse>> getCommentParent(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsInLecture(id));
    }

    @GetMapping("/getCommentCourse/{id}")
    public ResponseEntity<List<CommentInCourseResponse>> getCommentCourse(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentInCourse(id));
    }


    @PostMapping("/postCommentLecture")
    public ResponseEntity<CommentRequest> addComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.addComment(commentRequest));
    }

    @MessageMapping("/comments")
    @SendTo("/topic/comments")
    public CommentRequest postComment(@RequestBody CommentRequest commentRequest) {
        return commentService.addComment(commentRequest);
    }

    @PutMapping("/putComment")
    public ResponseEntity<CommentRequest> updateComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(commentRequest));
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(Map.of("message", "Comment "+id+" deleted successfully"));
    }
}