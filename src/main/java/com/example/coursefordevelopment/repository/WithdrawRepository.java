package com.example.coursefordevelopment.repository;

import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.entity.Withdraw;
import com.example.coursefordevelopment.enums.WithdrawStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
    List<Withdraw> findByUser(User user);

    List<Withdraw> findByStatus(WithdrawStatus withdrawStatus);
    List<Withdraw> findByUser_IdAndStatus(String userId, WithdrawStatus status);
}
