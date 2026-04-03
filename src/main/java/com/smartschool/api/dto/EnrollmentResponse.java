package com.smartschool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private String studentName;
    private String courseName;
    private String roomClass;
    private String grade;
}