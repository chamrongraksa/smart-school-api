package com.smartschool.api.repository;

import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Find all classes a specific student is taking
    List<Enrollment> findByStudentId(Long studentId);

    // Find all students sitting in a specific course
    List<Enrollment> findByCourseId(Long courseId);
    List<Enrollment> findByStudent(User student);
    List<Enrollment> findByCourseTeacherEmail(String email);

    List<Enrollment> findByStudentEmail(String name);
}