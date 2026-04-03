package com.smartschool.api.service;

import com.smartschool.api.entity.Attendance;
import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.repository.AttendanceRepository;
import com.smartschool.api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<Attendance> getAttendanceByEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));
        return attendanceRepository.findByEnrollment(enrollment);
    }

    @Transactional // 🌟 Ensures database integrity
    public Attendance markAttendance(Long enrollmentId, String status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));

        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setStatus(status);
        attendance.setDate(LocalDate.now());

        return attendanceRepository.save(attendance);
    }
}