package com.smartschool.api.controller;

import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getMyClasses(Principal principal) {
        // Find enrollments where the student_email matches the logged-in user
        List<Enrollment> myEnrollments = enrollmentRepository.findByStudentEmail(principal.getName());
        return ResponseEntity.ok(myEnrollments);
    }
}