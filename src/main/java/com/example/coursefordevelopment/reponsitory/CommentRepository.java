package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
