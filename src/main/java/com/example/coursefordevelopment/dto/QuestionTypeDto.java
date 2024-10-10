package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTypeDto {
    private Long id;
    private String typeName;
    private List<QuestionDto> questions;
}
