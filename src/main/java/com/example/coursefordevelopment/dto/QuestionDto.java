package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    QuestionDto {
    private int points;
    private Long quizId;           // Map ID của Quiz
    private Long questionTypeId;    // Map ID của QuestionType
    private String questionText;
    private List<OptionDto> options;
}
