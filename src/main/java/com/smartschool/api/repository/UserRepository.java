package com.smartschool.api.repository;

import com.smartschool.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Boot will automatically write the SQL to find a user by their email
    Optional<User> findByEmail(String email);

}