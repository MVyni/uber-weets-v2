package com.marcusvynicius.ecommerce_api.modules.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Schema(description = "User's full name", example = "John Doe")
    private String name;

    @Email(message = "Please enter a valid email address.")
    @Schema(description = "User's email address", example = "johndoe@email.com")
    private String email;

    @Length(min = 6, message = "Password must be at least 6 characters long.")
    @Schema(description = "User's password", example = "user_123")
    private String password_hash;

    @Schema(description = "User's phone number", example = "+1 (555) 123-4567")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Schema(description = "User's role in the system", example = "USER")
    private Role role;

    @Schema(description = "Indicates whether the user is active or not", example = "true")
    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
