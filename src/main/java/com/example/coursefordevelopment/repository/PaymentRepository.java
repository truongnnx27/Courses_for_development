package com.example.coursefordevelopment.repository;

import com.example.coursefordevelopment.entity.Payment;
import com.example.coursefordevelopment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.price) FROM Payment p WHERE p.user.id = ?1")
    BigDecimal getTotalPaymentsByUserId(String userId);
    Optional<Payment> findByPaymentId(String paymentId);

    List<Payment> findAllByUserId(String userId); // Đảm bảo phương thức này tồn tại
    List<Payment> findByUserAndPaymentStatus_Id(User user, Long paymentStatusId);

    List<Payment> findAllByPaymentStatusId(long l);

}
