package com.example.coursefordevelopment.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "Question_Types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String typeName;

    @OneToMany(mappedBy = "questionType")
    private List<Question> questions;
}

