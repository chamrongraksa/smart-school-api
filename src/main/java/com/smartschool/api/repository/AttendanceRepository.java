package com.smartschool.api.repository;

import com.smartschool.api.entity.Attendance;
import com.smartschool.api.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // 🌟 Automatically writes the SQL to fetch a student's attendance record for a specific class
    List<Attendance> findByEnrollment(Enrollment enrollment);
}