package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
