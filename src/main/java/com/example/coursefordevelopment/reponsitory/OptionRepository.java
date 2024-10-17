package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Option;
import com.example.coursefordevelopment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByQuestionId(Long id);
}
