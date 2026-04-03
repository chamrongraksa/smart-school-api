package com.smartschool.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // 🌟 ADD THIS
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // 🌟 FOR AUDITING

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor // Keep this for JPA
@AllArgsConstructor // Keep this for internal use
@Builder // 🌟 Pro-tip: This makes creating users much easier!
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @JsonIgnore
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 🌟 ADD THIS MANUAL CONSTRUCTOR for your Registration logic
    public User(String name, String email, String passwordHash, Role role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}