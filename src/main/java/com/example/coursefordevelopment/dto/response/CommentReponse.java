package com.example.coursefordevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentReponse {
    private Long id;
    private String idUserComment;
    private String commentText;
    private Long lectureId;
    private Long courseId;
    private int star;
    private Long parentId;
}
