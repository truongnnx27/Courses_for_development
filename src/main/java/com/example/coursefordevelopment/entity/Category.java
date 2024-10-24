package com.example.coursefordevelopment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Course_Tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_tag",columnDefinition = "nvarchar(100)")
    private String nameTag;

    @Column(name = "image",columnDefinition = "nvarchar(100)")
    private String image;

    @OneToMany(mappedBy = "category")
    private List<Course> course;

}

