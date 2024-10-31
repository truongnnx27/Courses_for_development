package com.example.coursefordevelopment.reponsitory;

import com.example.coursefordevelopment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query( value = "select " +
            "cate.category_name as categoryName, " +
            "cate.cover_image as coverImage, " +
            "count(u.id) as numberUser " +
            "from categories cate " +
            "inner join courses cou on cou.category_id = cate.id " +
            "inner join users u on u.id = cou.instructor_id " +
            "group by cate.category_name, cate.cover_image " +
            "order by numberUser desc ", nativeQuery = true)
    List<Object[]> finnCateNumberUser();
}
