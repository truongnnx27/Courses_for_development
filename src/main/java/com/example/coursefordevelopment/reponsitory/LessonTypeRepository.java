package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonTypeRepository extends JpaRepository<LessonType,Long> {
}
