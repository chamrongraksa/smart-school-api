package com.smartschool.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    // e.g., "PRESENT", "ABSENT", "LATE"
    @Column(nullable = false, length = 20)
    private String status;

    // 🔗 RELATIONAL JOIN: Many Attendance records belong to ONE specific Enrollment (Student + Course)
    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
}