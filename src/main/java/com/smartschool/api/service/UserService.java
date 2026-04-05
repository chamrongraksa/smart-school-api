package com.smartschool.api.service;

import com.smartschool.api.entity.User;
import com.smartschool.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Rule 1: Get a list of everyone in the school
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Rule 2: Register a brand new user (Admin, Teacher, or Student)
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Stop! That email is already taken!");
        }

        // Scramble the password! (Using the correct Getters/Setters)
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    // Rule 3: Find a specific user by their email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}