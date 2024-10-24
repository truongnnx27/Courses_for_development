package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.dto.UserCommentDto;
import com.example.coursefordevelopment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "select " +
            "c.id as id, " +
            "u.full_name as fullName, " +
            "u.profile_picture as profilePicture, " +
            "c.comment_text as commentText, " +
            "userCommentParent.full_name as nameUserReply, " +
            "c.comment_id as parentId " +
            "from comments c " +
            "inner join users u on c.user_id = u.id " +
            "left join comments commentParent on c.comment_id = commentParent.id " +
            "left join users userCommentParent on commentParent.user_id = userCommentParent.id", nativeQuery = true)
    List<Object[]> getUserCommentLesson(Long id);


    @Modifying
    @Query("delete from Comment c where c.comment.id = :id")
    void deleteByReplyId(Long id);

    @Modifying
    @Query("delete from Comment c where c.id = :id")
    void deleteById(Long id);

    @Query("SELECT c from Comment c where c.comment.id = :id")
    List<Comment> findByReplyId(Long id);
}
