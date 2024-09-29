package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private Long id;
    private Long questionId;  // Map ID của Question
    private String optionText;
    private boolean isCorrect;
}
