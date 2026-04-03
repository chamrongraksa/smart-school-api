package com.smartschool.api.controller;

import com.smartschool.api.entity.Assignment;
import com.smartschool.api.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByCourse(courseId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> request) {
        try {
            Long courseId = Long.parseLong(request.get("courseId"));
            String title = request.get("title");
            String description = request.get("description");
            String dueDate = request.get("dueDate");

            Assignment saved = assignmentService.createAssignment(courseId, title, description, dueDate);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}