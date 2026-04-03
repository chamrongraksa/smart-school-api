package com.smartschool.api.controller;

import com.smartschool.api.dto.JwtResponse;
import com.smartschool.api.dto.LoginRequest;
import com.smartschool.api.entity.User;
import com.smartschool.api.repository.UserRepository;
import com.smartschool.api.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allows Next.js to communicate with this API
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 1. Authenticate the user (Checks email and hashed password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // 2. Set the authentication in the Spring Security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate the JWT Token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Fetch the user's ID and Role from the database to send back to Next.js
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        // 5. Return the JSON response containing the token and user details
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        ));
    }
}