package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepository extends JpaRepository<Lecture,Long> {
}
