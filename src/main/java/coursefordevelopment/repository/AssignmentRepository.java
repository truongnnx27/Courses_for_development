<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/AssignmentRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/AssignmentRepository.java

import com.example.coursefordevelopment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}
