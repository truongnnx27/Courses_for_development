package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    List<Wishlist> findByUser(User user); //tìm danh mục yêu thíc của user

    Optional<Wishlist> findByUserIdAndCourseId(Long user, Long course);
}
