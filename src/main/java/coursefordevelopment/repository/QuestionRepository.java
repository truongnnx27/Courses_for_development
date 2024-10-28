<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/QuestionRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/QuestionRepository.java

import com.example.coursefordevelopment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
