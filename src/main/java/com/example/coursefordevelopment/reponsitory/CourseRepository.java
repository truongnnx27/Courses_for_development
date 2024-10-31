package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.title = ?1")
    Course findByCourseName(String courseName);

    @Query("SELECT c FROM Course c ORDER BY c.createdAt DESC")
    List<Course> find6NewCourse(Pageable pageable);

    @Query(value = "select " +
            "cou.id, " +
            "cou.cover_image, " +
            "cou.title, " +
            "count(pay.user_id) as userPayment, " +
            "u.id as idIntructor, " +
            "u.fullname as fullNameIntructor " +
            "from courses cou " +
            "inner join payments pay on cou.id = pay.course_id " +
            "inner join users u on cou.instructor_id = u.id " +
            "where pay.enrollment = true " +
            "group by cou.id " +
            "order by  userPayment desc LIMIT 6", nativeQuery = true)
    List<Object[]> findCourseBestSale();
}
