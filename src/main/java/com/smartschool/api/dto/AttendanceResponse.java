package com.smartschool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AttendanceResponse {
    private Long id;
    private Long enrollmentId;
    private String status;
    private LocalDate date;
    private String studentName; // 🌟 Nice to have for the frontend
}