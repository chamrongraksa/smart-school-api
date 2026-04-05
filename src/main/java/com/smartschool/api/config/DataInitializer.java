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
            User admin = User.builder()
                    .name("System Administrator")
                    .email("admin@school.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }

        // 2. Ensure Test Environment Exists
        if (courseRepository.count() == 0) {
            System.out.println("⚙️ Injecting Test Gradebook Data...");

            // Create a Teacher using Builder
            User teacher = User.builder()
                    .name("Prof. Turing")
                    .email("turing@school.com")
                    .passwordHash(passwordEncoder.encode("password123"))
                    .role(Role.TEACHER)
                    .build();
            userRepository.save(teacher);

            // Create a Student using Builder
            User student = User.builder()
                    .name("Chamrong Raksa")
                    .email("chamrong@school.com")
                    .passwordHash(passwordEncoder.encode("password123"))
                    .role(Role.STUDENT)
                    .build();
            userRepository.save(student);

            // Create a Course
            Course course = new Course();
            course.setRoomClass("G-12A");
            course.setSubject("Information Communication Technology (ICT)");
            course.setTeacher(teacher);
            courseRepository.save(course);

            // Enroll the Student
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentRepository.save(enrollment);

            System.out.println("✅ TEST DATA READY: Log in as turing@school.com to test the Gradebook!");
        }
    }
}