package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.title = ?1")
    Course findByCourseName(String courseName);
}
