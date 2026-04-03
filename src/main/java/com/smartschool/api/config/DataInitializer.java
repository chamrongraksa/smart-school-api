package com.smartschool.api.config;

import com.smartschool.api.entity.Course;
import com.smartschool.api.entity.Enrollment;
import com.smartschool.api.entity.Role;
import com.smartschool.api.entity.User;
import com.smartschool.api.repository.CourseRepository;
import com.smartschool.api.repository.EnrollmentRepository;
import com.smartschool.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. Ensure Master Admin Exists
        if (userRepository.findByEmail("admin@school.com").isEmpty()) {
            User admin = new User(null, "System Administrator", "admin@school.com", passwordEncoder.encode("admin123"), Role.ADMIN);
            userRepository.save(admin);
        }

        // 2. Ensure Test Environment Exists (Teacher, Student, Course, Enrollment)
        if (courseRepository.count() == 0) {
            System.out.println("⚙️ Injecting Test Gradebook Data...");

            // Create a Teacher
            User teacher = new User(null, "Prof. Turing", "turing@school.com", passwordEncoder.encode("password123"), Role.TEACHER);
            userRepository.save(teacher);

            // Create a Student
            User student = new User(null, "Chamrong Raksa", "chamrong@school.com", passwordEncoder.encode("password123"), Role.STUDENT);
            userRepository.save(student);

            // Create a Course using the Cambodian Curriculum
            Course course = new Course();
            course.setRoomClass("G-12A"); // The high school class room
            course.setSubject("Information Communication Technology (ICT)"); // The official subject
            course.setTeacher(teacher);
            courseRepository.save(course);

            // Enroll the Student in the Course
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentRepository.save(enrollment);

            System.out.println("✅ TEST DATA READY: Log in as turing@school.com to test the Gradebook!");
        }
    }
}