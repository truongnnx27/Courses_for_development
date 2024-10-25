package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private String title;
    private String description;
    private String category;
    private String coverImage;
    private BigDecimal price;
    private boolean published;
    private String level;
    private Long instructor;
    private List<SectionDto> sections = new ArrayList<>();
}
