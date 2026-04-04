package com.smartschool.api.controller;

import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/teacher") // 🌟 Matches the Next.js fetch URL perfectly
@RequiredArgsConstructor
public class TeacherController {

    private final EnrollmentRepository enrollmentRepository;

    // 1. Get the classes for the logged-in teacher
    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getMyStudents(Principal principal) {

        // principal.getName() gets the email of the person who is currently logged in via the Token
        String teacherEmail = principal.getName();

        // Fetch only the enrollments assigned to this specific teacher
        List<Enrollment> myClasses = enrollmentRepository.findByCourseTeacherEmail(teacherEmail);

        return ResponseEntity.ok(myClasses);
    }
}