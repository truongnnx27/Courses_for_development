package com.example.coursefordevelopment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(columnDefinition = "nvarchar(255)")
    private String category;

    private BigDecimal rating = BigDecimal.ZERO;

    private int numberOfRatings = 0;

    private int numberOfStudents = 0;

    private BigDecimal price = BigDecimal.ZERO;

    private String language = "English";

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_level_id")
    private CourseLevel courseLevel;

    @OneToMany(mappedBy = "course")
    private List<Comment> comments;

    @OneToMany(mappedBy = "courseEntity")
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "course")
    private List<Wishlist> wishlists ;
}

