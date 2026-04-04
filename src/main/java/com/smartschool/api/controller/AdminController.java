package com.smartschool.api.controller;

import com.smartschool.api.entity.Course;
import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.entity.Role;
import com.smartschool.api.entity.User;
import com.smartschool.api.repository.CourseRepository;
import com.smartschool.api.repository.EnrollmentRepository;
import com.smartschool.api.repository.UserRepository;
import com.smartschool.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
// 🌟 Change it from "http://localhost:3000" to allow all traffic (perfect for a school project)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository; // 🌟 NEW: Added to save enrollments

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        User newUser = new User();
        newUser.setName(request.get("name"));
        newUser.setEmail(request.get("email"));
        newUser.setRole(Role.valueOf(request.get("role")));

        // 🌟 THE FIX: Map "password" (from Next.js) to "passwordHash" (the entity field)
        String plainPassword = request.get("password");
        newUser.setPasswordHash(plainPassword);

        return ResponseEntity.ok(userService.registerUser(newUser));
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<User>> getTeachers() {
        return ResponseEntity.ok(userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.TEACHER)
                .collect(Collectors.toList()));
    }

    // 🌟 NEW: Fetch only students for the dropdown
    @GetMapping("/students")
    public ResponseEntity<List<User>> getStudents() {
        return ResponseEntity.ok(userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.STUDENT)
                .collect(Collectors.toList()));
    }

    // 🌟 NEW: Fetch all created classes for the dropdown
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody Map<String, String> request) {
        try {
            String roomClass = request.get("roomClass");
            String subject = request.get("subject");
            User teacher = userRepository.findById(Long.parseLong(request.get("teacherId")))
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            Course course = new Course();
            course.setRoomClass(roomClass);
            course.setSubject(subject);
            course.setTeacher(teacher);

            return ResponseEntity.ok(courseRepository.save(course));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create class: " + e.getMessage());
        }
    }

    // 🌟 NEW: The bridge! Connects a student to a class
    @PostMapping("/enrollments")
    public ResponseEntity<?> enrollStudent(@RequestBody Map<String, String> request) {
        try {
            User student = userRepository.findById(Long.parseLong(request.get("studentId")))
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            Course course = courseRepository.findById(Long.parseLong(request.get("courseId")))
                    .orElseThrow(() -> new RuntimeException("Class not found"));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            return ResponseEntity.ok(enrollmentRepository.save(enrollment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to enroll: " + e.getMessage());
        }
    }
    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        // This fetches all enrollments from the database and sends them to the dashboard
        return ResponseEntity.ok(enrollmentRepository.findAll());
    }
}