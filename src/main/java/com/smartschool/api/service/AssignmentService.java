package com.smartschool.api.service;

import com.smartschool.api.entity.Assignment;
import com.smartschool.api.entity.Course;
import com.smartschool.api.repository.AssignmentRepository;
import com.smartschool.api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        return assignmentRepository.findByCourse(course);
    }

    @Transactional
    public Assignment createAssignment(Long courseId, String title, String description, String dueDate) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Assignment assignment = new Assignment();
        assignment.setCourse(course);
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDueDate(LocalDate.parse(dueDate));

        return assignmentRepository.save(assignment);
    }
}