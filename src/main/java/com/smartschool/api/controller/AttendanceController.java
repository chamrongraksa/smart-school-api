package com.smartschool.api.controller;

import com.smartschool.api.entity.Attendance;
import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.repository.AttendanceRepository;
import com.smartschool.api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
// 🌟 Change it from "http://localhost:3000" to allow all traffic (perfect for a school project)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;

    // 🌟 NEW: Fetch all attendance records for a specific enrollment
    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<?> getAttendance(@PathVariable Long enrollmentId) {
        try {
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                    .orElseThrow(() -> new RuntimeException("Enrollment not found"));

            // Uses the finder method we wrote in the Repository earlier!
            return ResponseEntity.ok(attendanceRepository.findByEnrollment(enrollment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch attendance: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> markAttendance(@RequestBody Map<String, String> request) {
        try {
            Long enrollmentId = Long.parseLong(request.get("enrollmentId"));
            String status = request.get("status"); // We expect "PRESENT", "LATE", or "ABSENT"

            // 1. Find the specific student's enrollment record
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                    .orElseThrow(() -> new RuntimeException("Enrollment not found"));

            // 2. Create the attendance record for today
            Attendance attendance = new Attendance();
            attendance.setEnrollment(enrollment);
            attendance.setStatus(status);
            attendance.setDate(LocalDate.now()); // Automatically grabs today's date

            // 3. Save to the database
            return ResponseEntity.ok(attendanceRepository.save(attendance));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to mark attendance: " + e.getMessage());
        }
    }
}