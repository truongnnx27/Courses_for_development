package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.UserCommentDto;
import com.example.coursefordevelopment.entity.Comment;
import com.example.coursefordevelopment.entity.Lesson;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.mapstruct.CommentMapper;
import com.example.coursefordevelopment.reponsitory.CommentRepository;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.LessonRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {

    LessonRepository lessonRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    CommentRepository commentRepository;

    public Comment addComment(CommentDto commentDto) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);

        if (commentDto.getUserId() != null) {
            User user = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentDto.getLessonId() != null) {
            Lesson lesson = lessonRepository.findById(commentDto.getLessonId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLesson(lesson);
        } else {
            comment.setLesson(null);
        }

        if (commentDto.getCommentId() != null) {
            Comment reply = commentRepository.findById(commentDto.getCommentId()).orElseThrow(() -> new RuntimeException("Not found Reply"));
            comment.setComment(reply);
        } else {
            comment.setComment(null);
        }

        comment.setCourse(null);
        return commentRepository.save(comment);
    }

    public Comment findCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found Comment"));
    }

    public boolean isCommentExist(long id) {
        if (commentRepository.existsById(id)){
            return true;
        } else {
            new RuntimeException("Not found Comment");
            return false;
        }
    }

    public void deleteComment(long id) {
        Comment commentParent = findCommentById(id); // Tìm comment cha
        while (commentParent != null) {
            List<Comment> commentChilds = commentRepository.findByReplyId(commentParent.getId()); // Lấy ra comment con
            if (commentChilds.size() <= 0) { //Nếu không có comment con thì xóa
                commentRepository.delete(commentParent);
                commentParent = commentParent.getComment();
            } else { // Nếu có comment con thì chuyển comment con tiếp theo làm cha và tiếp tục vòng lặp
                commentParent = commentChilds.get(0);
            }
        }
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<UserCommentDto> findCommentsByLessonId(long id) {
        List<Object[]> results = commentRepository.getUserCommentLesson(id);
        List<UserCommentDto> comments = results.stream()
                .map(result -> new UserCommentDto(
                        (Long) result[0],         // id
                        (String) result[1],       // fullName
                        (String) result[2],       // profilePicture
                        (String) result[3],    // commentText
                        (String) result[4],       // nameUserReply
                        (Long) result[5]          // parentId
                ))
                .collect(Collectors.toList());
        return comments;
    }

}
