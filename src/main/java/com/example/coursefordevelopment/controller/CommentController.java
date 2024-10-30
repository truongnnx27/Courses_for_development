package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.request.CommentInLectureReques;
import com.example.coursefordevelopment.dto.response.CommentReponse;
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
    public ResponseEntity<List<CommentInLectureReques>> getCommentParent(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsInLecture(id));
    }

    @MessageMapping("/comments")
    @SendTo("/topic/comments")
    public List<CommentInLectureReques> getCommentParent_Socket(Long id) {
        return commentService.getCommentsInLecture(id);
    }

    @PostMapping("/postCommentLecture")
    public ResponseEntity<CommentReponse> addComment(@RequestBody CommentReponse commentReponse) {
        return ResponseEntity.ok(commentService.addComment(commentReponse));
    }

    @PutMapping("/putComment")
    public ResponseEntity<CommentReponse> updateComment(@RequestBody CommentReponse commentReponse) {
        return ResponseEntity.ok(commentService.updateComment(commentReponse));
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(Map.of("message", "Comment "+id+" deleted successfully"));
    }
}