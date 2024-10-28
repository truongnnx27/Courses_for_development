<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/QuizRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/QuizRepository.java

import com.example.coursefordevelopment.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
