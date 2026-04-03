package com.smartschool.api.dto;

import lombok.Data;

@Data
public class AttendanceRequest {
    private Long enrollmentId;
    private String status; // PRESENT, LATE, ABSENT
}