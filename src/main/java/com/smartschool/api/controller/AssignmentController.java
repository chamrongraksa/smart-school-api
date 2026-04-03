package com.smartschool.api.controller;

import com.smartschool.api.entity.Assignment;
import com.smartschool.api.entity.Course;
import com.smartschool.api.repository.AssignmentRepository;
import com.smartschool.api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
// 🌟 Change it from "http://localhost:3000" to allow all traffic (perfect for a school project)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    // 🌟 1. Create a new assignment
    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody Map<String, String> request) {
        try {
            Long courseId = Long.parseLong(request.get("courseId"));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Class not found"));

            Assignment assignment = new Assignment();
            assignment.setTitle(request.get("title"));
            assignment.setDescription(request.get("description"));
            // Parses the HTML date picker format (YYYY-MM-DD) perfectly into Java
            assignment.setDueDate(LocalDate.parse(request.get("dueDate")));
            assignment.setCourse(course);

            return ResponseEntity.ok(assignmentRepository.save(assignment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create assignment: " + e.getMessage());
        }
    }

    // 🌟 2. Fetch assignments for a specific class (We will use this for the Student Portal next!)
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Long courseId) {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            return ResponseEntity.ok(assignmentRepository.findByCourse(course));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}