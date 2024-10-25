package com.example.coursefordevelopment.repository;

import com.example.coursefordevelopment.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
