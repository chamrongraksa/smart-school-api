        existingCourse.setRoomClass(updatedInfo.getRoomClass());
        existingCourse.setSubject(updatedInfo.getSubject());
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody Course updatedInfo) {
package com.smartschool.api.controller;

import com.smartschool.api.entity.Course;
import com.smartschool.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Use a CourseDTO here if Course has complex relationships
    @GetMapping
    public List<Course> list() {
        return courseService.getAllCourses();
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return courseService.createCourse(course);
    }
}