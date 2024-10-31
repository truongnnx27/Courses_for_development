package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query(value = "select " +
            "u.id, " +
            "u.fullname, " +
            "u.avatar_url, " +
            "count(us.id) as numberUserPayment " +
            "from users u " +
            "inner join courses cou on u.id = cou.instructor_id " +
            "inner join payments pay on cou.id = pay.course_id " +
            "inner join users us on us.id = pay.user_id " +
            "where pay.enrollment = true " +
            "group by u.id, u.fullname, u.avatar_url " +
            "order by numberUserPayment desc", nativeQuery = true)
    List<Object[]> findTopIntructor();
}
