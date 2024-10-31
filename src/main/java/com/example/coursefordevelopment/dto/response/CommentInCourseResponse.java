package com.example.coursefordevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentInCourseResponse {
    private Long id;
    private String fullName;
    private String userId;
    private String profilePicture;
    private String commentText;
    private Long courseId;
    private int star;
}
