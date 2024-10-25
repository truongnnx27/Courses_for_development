package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  QuestionDto {
    private Long id;
    private int points;
    private String type;    // Map ID của QuestionType
    private String title;
    private List<OptionDto> options;

}
