package com.example.coursefordevelopment.dto;

import com.example.coursefordevelopment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDto {
    private Long id;
    private String fullName;
    private String profilePicture;
    private String commentText;
//    private LocalDateTime updatedAt;
    private String nameUserReply;
    private Long parentId;
}