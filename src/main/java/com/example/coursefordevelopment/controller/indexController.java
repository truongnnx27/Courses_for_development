package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.reponsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public String index(
            Model model
    ) {
        model.addAttribute("listComment", commentRepository.findAll());
        return "index";
    }
}
