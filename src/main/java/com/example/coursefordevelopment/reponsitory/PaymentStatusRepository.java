package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    PaymentStatus findByStatusName(String completed);
}
