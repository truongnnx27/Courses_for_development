package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u.email from User u where u.id = :id")
    String findEmailById(Long id);
}
