<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/PaymentStatusRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/PaymentStatusRepository.java

import com.example.coursefordevelopment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
}
