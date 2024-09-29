package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
