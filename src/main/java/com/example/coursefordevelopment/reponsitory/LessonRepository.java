package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
}
