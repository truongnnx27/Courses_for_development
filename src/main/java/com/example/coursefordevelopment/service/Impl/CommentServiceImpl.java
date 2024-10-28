package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.UserCommentDto;
import com.example.coursefordevelopment.entity.Comment;
import com.example.coursefordevelopment.entity.Lecture;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.mapstruct.CommentMapper;
import com.example.coursefordevelopment.repository.CommentRepository;
import com.example.coursefordevelopment.repository.LectureRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    LectureRepository lectureRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Override
    public Comment addComment(CommentDto commentDto) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);

        if (commentDto.getUserId() != null) {
            User user = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentDto.getLectureId() != null) {
            Lecture lecture = lectureRepository.findById(commentDto.getLectureId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLecture(lecture);
        } else {
            comment.setLecture(null);
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

    @Override
    public Comment findCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found Comment"));
    }

    @Override
    public boolean isCommentExist(long id) {
        if (commentRepository.existsById(id)){
            return true;
        } else {
            new RuntimeException("Not found Comment");
            return false;
        }
    }

    @Override
    public void deleteComment(long id) {
        Comment commentIndex = commentRepository.findById(id).orElse(null);
        while (commentIndex != null) {
            List<Comment> commentChilds = commentRepository.findByReplyId(commentIndex.getId()); // Lấy ra comment con
            if (commentChilds.size() <= 0) { //Nếu không có comment con thì xóa
                commentRepository.delete(commentIndex);
                commentIndex = commentRepository.findById(id).orElse(null);
            } else { // Nếu có comment con thì chuyển comment con tiếp theo làm cha và tiếp tục vòng lặp
                commentIndex = commentChilds.get(0);
            }
        }
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<UserCommentDto> findCommentsByLessonId(long id) {
        List<Object[]> results = commentRepository.getCommentLesson(id);
        List<UserCommentDto> comments = results.stream()
                .map(result -> new UserCommentDto(
                        (Long) result[0],         // id
                        (String) result[1],       // fullName
                        (String) result[2],           //idUserComment
                        (String) result[3],       // profilePicture
                        (String) result[4],    // commentText
                        (String) result[5],       // nameUserReply
                        (Long) result[6]         // parentId
                ))
                .collect(Collectors.toList());
        return comments;
    }

    @Override
    public Comment putComment(long id, CommentDto commentDto) {
        Comment comment = findCommentById(id);
        Comment commentUpdate = commentMapper.commentDtoToComment(commentDto);
        comment.setCommentText(commentUpdate.getCommentText());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
