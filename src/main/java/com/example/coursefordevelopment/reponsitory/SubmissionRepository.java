package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
