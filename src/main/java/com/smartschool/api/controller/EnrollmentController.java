package com.smartschool.api.controller;

import com.smartschool.api.dto.EnrollmentResponse;
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
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PutMapping("/{id}/grade")
    public ResponseEntity<EnrollmentResponse> updateGrade(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newGrade = body.get("grade");
        Enrollment updated = enrollmentService.updateGrade(id, newGrade);

        EnrollmentResponse response = new EnrollmentResponse(
                updated.getId(),
                updated.getStudent().getName(),
                updated.getCourse().getSubject(),
                updated.getCourse().getRoomClass(),
                updated.getGrade()
        );

        return ResponseEntity.ok(response);
    }
}