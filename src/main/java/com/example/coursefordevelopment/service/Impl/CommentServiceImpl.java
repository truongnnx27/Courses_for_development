package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.request.CommentInLectureReques;
import com.example.coursefordevelopment.dto.response.CommentReponse;
import com.example.coursefordevelopment.entity.Comment;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.entity.Lecture;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.mapstruct.CommentMapper;
import com.example.coursefordevelopment.reponsitory.CommentRepository;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.LectureRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
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
    CourseRepository courseRepository;
    @Override
    public CommentReponse addComment(CommentReponse commentReponse) {

        Comment comment = commentMapper.commentRepsToComment(commentReponse);
        if (commentReponse.getIdUserComment() != null) {
            User user = userRepository.findById(commentReponse.getIdUserComment()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentReponse.getLectureId() != null) {
            Lecture lecture = lectureRepository.findById(commentReponse.getLectureId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLecture(lecture);
        } else {
            comment.setLecture(null);
        }

        if (commentReponse.getParentId() != null) {
            Comment reply = commentRepository.findById(commentReponse.getParentId()).orElseThrow(() -> new RuntimeException("Not found Reply"));
            comment.setComment(reply);
        } else {
            comment.setComment(null);
        }

        if(commentReponse.getCourseId() != null) {
            Course course = courseRepository.findById(commentReponse.getCourseId()).orElseThrow(() -> new RuntimeException("Not found Course"));
        } else {
            comment.setCourse(null);
        }

        commentRepository.save(comment);
        return commentMapper.commentToCommentReps(comment);
    }

    @Override
    public CommentReponse updateComment(CommentReponse commentReponse) {
        Comment comment = commentMapper.commentRepsToComment(commentReponse);
        if (commentReponse.getIdUserComment() != null) {
            User user = userRepository.findById(commentReponse.getIdUserComment()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentReponse.getLectureId() != null) {
            Lecture lecture = lectureRepository.findById(commentReponse.getLectureId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLecture(lecture);
        } else {
            comment.setLecture(null);
        }

        if (commentReponse.getParentId() != null) {
            Comment reply = commentRepository.findById(commentReponse.getParentId()).orElseThrow(() -> new RuntimeException("Not found Reply"));
            comment.setComment(reply);
        } else {
            comment.setComment(null);
        }

        if(commentReponse.getCourseId() != null) {
            Course course = courseRepository.findById(commentReponse.getCourseId()).orElseThrow(() -> new RuntimeException("Not found Course"));
        } else {
            comment.setCourse(null);
        }
        commentRepository.save(comment);
        return commentMapper.commentToCommentReps(comment);
    }

    @Override
    public List<CommentInLectureReques> getCommentsInLecture(long lectureId) {
        List<Object[]> results = commentRepository.getCommentLecture(lectureId);
        List<CommentInLectureReques> comments = results.stream()
                .map(result -> new CommentInLectureReques(
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
    public void deleteComment(long commentId) {
        Comment commentIndex = commentRepository.findById(commentId).orElse(null);

        while (commentIndex != null) {
            List<Comment> commentChilds = commentRepository.findByReplyId(commentIndex.getId()); // Lấy ra comment con
            if (commentChilds.size() <= 0) { //Nếu không có comment con thì xóa
                commentRepository.delete(commentIndex);
                commentIndex = commentRepository.findById(commentId).orElse(null);
            } else { // Nếu có comment con thì chuyển comment con tiếp theo làm cha và tiếp tục vòng lặp
                commentIndex = commentChilds.get(0);
            }
        }
    }
}
