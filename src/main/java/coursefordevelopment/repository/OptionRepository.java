<<<<<<<< HEAD:src/main/java/coursefordevelopment/repository/OptionRepository.java
package coursefordevelopment.repository;
========
package com.example.coursefordevelopment.repository;
>>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/repository/OptionRepository.java

import com.example.coursefordevelopment.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
