package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Section;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<Section, Long> {
}
