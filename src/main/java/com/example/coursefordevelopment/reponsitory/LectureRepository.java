package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.dto.LectureDto;
import com.example.coursefordevelopment.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findBySectionId(Long sectionId);
}
