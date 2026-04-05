package com.smartschool.api.repository;

import com.smartschool.api.entity.Assignment;
import com.smartschool.api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByCourse(Course course);
}