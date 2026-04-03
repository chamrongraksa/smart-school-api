package com.smartschool.api.repository;

import com.smartschool.api.entity.Assignment;
import com.smartschool.api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // 🌟 Automatically writes the SQL to find all assignments for a specific course
    List<Assignment> findByCourse(Course course);
}