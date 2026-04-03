package com.smartschool.api.controller;

import com.smartschool.api.dto.AttendanceRequest;
import com.smartschool.api.dto.AttendanceResponse;
import com.smartschool.api.entity.Attendance;
import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.repository.AttendanceRepository;
import com.smartschool.api.repository.EnrollmentRepository;
import com.smartschool.api.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService; // 🌟 Inject Service, not Repositories

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendance(@PathVariable Long enrollmentId) {
        List<Attendance> records = attendanceService.getAttendanceByEnrollment(enrollmentId);

        List<AttendanceResponse> response = records.stream()
                .map(att -> new AttendanceResponse(
                        att.getId(),
                        enrollmentId,
                        att.getStatus(),
                        att.getDate(),
                        att.getEnrollment().getStudent().getName()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> markAttendance(@RequestBody AttendanceRequest request) {
        Attendance saved = attendanceService.markAttendance(request.getEnrollmentId(), request.getStatus());

        return ResponseEntity.ok(new AttendanceResponse(
                saved.getId(),
                request.getEnrollmentId(),
                saved.getStatus(),
                saved.getDate(),
                saved.getEnrollment().getStudent().getName()
        ));
    }
}