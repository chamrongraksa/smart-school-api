package com.smartschool.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The Student (User)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    // The Course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String grade;

    // When did they join the class?
    private LocalDateTime enrollmentDate = LocalDateTime.now();
}