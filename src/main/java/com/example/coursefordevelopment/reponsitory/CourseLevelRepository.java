package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseLevelRepository extends JpaRepository<CourseLevel,Long> {
}
