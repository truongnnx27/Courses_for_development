package com.example.coursefordevelopment.repository;

import com.example.coursefordevelopment.entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
    Withdraw findByToken(String token);
}
