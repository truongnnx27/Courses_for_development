package com.example.coursefordevelopment.dto.response;

import com.example.coursefordevelopment.dto.SectionDto;
import com.example.coursefordevelopment.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Long categoryId;
    private String coverImage;
    private BigDecimal price;
    private boolean published;
    private String level;
    private UserResponse instructor;
    private List<SectionDto> sections = new ArrayList<>();
}
