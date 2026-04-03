package com.smartschool.api.controller;

import com.smartschool.api.entity.User;
import com.smartschool.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // ⬅️ ADD THIS LINE!
@RequiredArgsConstructor
public class UserController {

    // The Front Desk worker needs a phone to call the Manager!
    private final UserService userService;

    // Route 1: When someone visits http://localhost:8081/api/users, show everyone!
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Call the manager to get the list
    }

    // Route 2: When the Next.js website sends a new registration form, save it!
    @PostMapping("/register")
    public User register(@RequestBody User user) { // Must have @RequestBody!
        return userService.registerUser(user);
    }

}