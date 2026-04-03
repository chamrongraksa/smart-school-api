package com.smartschool.api.controller;

import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.entity.Role; // Imported our new Enum
import com.smartschool.api.entity.User;
import com.smartschool.api.repository.EnrollmentRepository;
import com.smartschool.api.service.EnrollmentService;
import com.smartschool.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor // This Lombok annotation cleanly handles our Autowiring!
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @PostMapping
    public Enrollment enrollStudent(@RequestBody Enrollment enrollment) {
        return enrollmentService.enroll(enrollment);
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getStudentClasses(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getCourseEnrollments(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }

    @GetMapping
    public ResponseEntity<?> getEnrollments() {
        // 1. Ask the Security Guard who is currently making the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // The JWT token stores the email here

        // 2. Find the user in the database using our new Email method!
        User currentUser = userService.getUserByEmail(email);

        if (currentUser == null) {
            return ResponseEntity.status(403).body("User not found");
        }

        // 3. Compare using our strict Role Enum
        if (currentUser.getRole() == Role.TEACHER || currentUser.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(enrollmentRepository.findAll());
        }

        // 4. If they are a STUDENT, ONLY give them their specific classes
        return ResponseEntity.ok(enrollmentRepository.findByStudent(currentUser));
    }

    // Consolidated to a single PutMapping to prevent Spring Boot crashes
    @PutMapping("/{id}/grade")
    public ResponseEntity<?> updateGrade(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newGrade = request.get("grade");
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);

        if (enrollment == null) {
            return ResponseEntity.badRequest().body("Enrollment not found");
        }

        enrollment.setGrade(newGrade);
        enrollmentRepository.save(enrollment);

        return ResponseEntity.ok(enrollment);
    }
}