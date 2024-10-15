package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.CoursePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<CoursePayment, Long> {
}
