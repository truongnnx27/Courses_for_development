package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
