package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.QuestionDto;
import com.example.coursefordevelopment.dto.QuizDto;
import com.example.coursefordevelopment.dto.Request.ApiRespone;
import com.example.coursefordevelopment.dto.Request.AppException;
import com.example.coursefordevelopment.entity.Quiz;
import com.example.coursefordevelopment.service.QuizzService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getQuestionByQuiz/{id}")
    public ResponseEntity<ApiRespone<List<QuestionDto>>> getQuestionByQuiz(@PathVariable Long id)
    {
        List<QuestionDto> questionDtos = quizzService.getQuestionByQuiz(id);
        ApiRespone<List<QuestionDto>> apiResponse = new ApiRespone<>(200, "Questions retrieved successfully", questionDtos);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiRespone<Long>> deleteQuiz(@PathVariable Long id)
    {
            quizzService.deleteQuiz(id);
            ApiRespone<Long> apiRespone = new ApiRespone<>(9999,"Quiz deleted successfully",id);
            return ResponseEntity.ok(apiRespone);
    }

}
