package com.example.coursefordevelopment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentInLectureReques {
    private Long id;
    private String fullName;
    private String idUserComment;
    private String profilePicture;
    private String commentText;
    private String nameUserReply;
    private Long parentId;
}
