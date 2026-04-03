package com.smartschool.api.controller;

import com.smartschool.api.dto.UserResponse;
import com.smartschool.api.entity.User;
import com.smartschool.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        // Convert Entities to DTOs manually or with a mapper
        return userService.getAllUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole().name()))
                .toList();
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole().name());
    }
}