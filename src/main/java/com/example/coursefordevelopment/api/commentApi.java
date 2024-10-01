package com.example.coursefordevelopment.api;

import com.example.coursefordevelopment.entity.Comment;
import com.example.coursefordevelopment.reponsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class commentApi {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/getallComment")
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
    
}
