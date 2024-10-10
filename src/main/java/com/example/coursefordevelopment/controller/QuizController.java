package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.QuestionDto;
import com.example.coursefordevelopment.dto.QuizDto;
import com.example.coursefordevelopment.dto.Request.ApiRespone;
import com.example.coursefordevelopment.entity.Quiz;
import com.example.coursefordevelopment.service.QuizzService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/api/quiz")
public class QuizController {
    QuizzService quizzService;

    @PostMapping("/addNew")
    public ResponseEntity<ApiRespone<QuizDto>> createQuiz(@RequestBody QuizDto quizDto)
    {
        QuizDto quiz = quizzService.create(quizDto);
        ApiRespone<QuizDto> apiRespone = new ApiRespone<>(9898,"Quiz created successfully",quiz);
        return  ResponseEntity.ok(apiRespone);
    }
}
