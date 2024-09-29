package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoReponsitory extends JpaRepository<Video, Long> {

}
