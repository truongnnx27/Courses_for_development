package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.UserCommentDto;
import com.example.coursefordevelopment.entity.Comment;
import com.example.coursefordevelopment.mapstruct.CommentMapper;
import com.example.coursefordevelopment.reponsitory.CommentRepository;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.LessonRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentController {

    CommentMapper commentMapper;
    CommentService commentService;

    //Truy xuát tất cả comment
    @GetMapping("/getallComment")
    public ResponseEntity<List<CommentDto>> getallComments() {
        return ResponseEntity.ok(commentMapper.listCommentToListCommentDto(commentService.findAllComments()));
    }

    //Truy xuất comment theo lesson
    @GetMapping("/getCommentLesson/{id}")
    public ResponseEntity<List<UserCommentDto>> getCommentParent(@PathVariable Long id) {
        return ResponseEntity.ok( commentService.findCommentsByLessonId(id));
    }

    @PostMapping("/postCommentLesson")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        Comment comment = commentService.addComment(commentDto);
        CommentDto responseDto = commentMapper.commentToCommentDto(comment);
        return ResponseEntity.ok(responseDto);
    }
//
//    @PutMapping("/putComment/{id}")
//    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
//
//    }
//
    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long id) {
        if (commentService.isCommentExist(id)) {
            Comment comment = commentService.findCommentById(id);
            commentService.deleteComment(id);
            return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
