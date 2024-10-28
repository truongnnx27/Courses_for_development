<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/CategoryRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/CategoryRepository.java

import com.example.coursefordevelopment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
