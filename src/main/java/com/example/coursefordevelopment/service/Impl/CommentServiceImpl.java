package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.CommentDto;
import com.example.coursefordevelopment.dto.response.CommentInCourseResponse;
import com.example.coursefordevelopment.dto.response.CommentInLectureResponse;
import com.example.coursefordevelopment.dto.request.CommentRequest;
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
    public CommentRequest addComment(CommentRequest commentRequest) {

        Comment comment = commentMapper.commentRepsToComment(commentRequest);
        if (commentRequest.getIdUserComment() != null) {
            User user = userRepository.findById(commentRequest.getIdUserComment()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentRequest.getLectureId() != null) {
            Lecture lecture = lectureRepository.findById(commentRequest.getLectureId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLecture(lecture);
        } else {
            comment.setLecture(null);
        }

        if (commentRequest.getParentId() != null) {
            Comment reply = commentRepository.findById(commentRequest.getParentId()).orElseThrow(() -> new RuntimeException("Not found Reply"));
            comment.setComment(reply);
        } else {
            comment.setComment(null);
        }

        if(commentRequest.getCourseId() != null) {
            Course course = courseRepository.findById(commentRequest.getCourseId()).orElseThrow(() -> new RuntimeException("Not found Course"));
        } else {
            comment.setCourse(null);
        }

        commentRepository.save(comment);
        return commentMapper.commentToCommentReps(comment);
    }

    @Override
    public CommentRequest updateComment(CommentRequest commentRequest) {
        Comment comment = commentMapper.commentRepsToComment(commentRequest);
        if (commentRequest.getIdUserComment() != null) {
            User user = userRepository.findById(commentRequest.getIdUserComment()).orElseThrow(() -> new RuntimeException("Not found User"));
            comment.setUser(user);
        } else {
            comment.setUser(null);
        }

        if (commentRequest.getLectureId() != null) {
            Lecture lecture = lectureRepository.findById(commentRequest.getLectureId()).orElseThrow(() -> new RuntimeException("Not found Lesson"));
            comment.setLecture(lecture);
        } else {
            comment.setLecture(null);
        }

        if (commentRequest.getParentId() != null) {
            Comment reply = commentRepository.findById(commentRequest.getParentId()).orElseThrow(() -> new RuntimeException("Not found Reply"));
            comment.setComment(reply);
        } else {
            comment.setComment(null);
        }

        if(commentRequest.getCourseId() != null) {
            Course course = courseRepository.findById(commentRequest.getCourseId()).orElseThrow(() -> new RuntimeException("Not found Course"));
        } else {
            comment.setCourse(null);
        }
        commentRepository.save(comment);
        return commentMapper.commentToCommentReps(comment);
    }

    @Override
    public List<CommentInLectureResponse> getCommentsInLecture(long lectureId) {
        List<Object[]> results = commentRepository.getCommentLecture(lectureId);
        List<CommentInLectureResponse> comments = results.stream()
                .map(result -> new CommentInLectureResponse(
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
    public List<CommentInCourseResponse> getCommentInCourse(long courseId) {
        return commentsToCommentDtosStream(commentRepository.findCommentsByCourse_Id(courseId));
    }


    public List<CommentInCourseResponse> commentsToCommentDtosStream(List<Comment> comments) {
        return comments.stream().map(comment -> {
            CommentInCourseResponse response = new CommentInCourseResponse();
            response.setUserId(comment.getUser().getId());
            response.setCourseId(comment.getCourse().getId());
            response.setProfilePicture(comment.getUser().getAvatarUrl());
            response.setFullName(comment.getUser().getFullname());
            response.setCommentText(comment.getCommentText());
            response.setId(comment.getId());
            response.setStar(comment.getStar());
            // Thêm các trường khác nếu cần
            return response;
        }).collect(Collectors.toList());
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
