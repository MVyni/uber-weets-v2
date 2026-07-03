package com.marcusvynicius.ecommerce_api.user;

import com.marcusvynicius.ecommerce_api.exceptions.InvalidCredentialsException;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.user.Role;
import com.marcusvynicius.ecommerce_api.modules.user.UserEntity;
import com.marcusvynicius.ecommerce_api.modules.user.UserRepository;
import com.marcusvynicius.ecommerce_api.modules.user.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "jwtSecret", "secretKey");
    }

    @Test
    public void should_be_able_to_sign_in() {

        var login = new AuthRequestDTO("admin@uberweets.local", "Admin@123");

        var user = UserEntity.builder()
                .id(UUID.randomUUID())
                .name("admin")
                .email("admin@uberweets.local")
                .password_hash("$we5&345hash")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findByEmail("admin@uberweets.local")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Admin@123", "$we5&345hash")).thenReturn(true);

        var response = authService.execute(login);

        assertNotNull(response);
        assertNotNull(response.getAccess_token());
        assertNotNull(response.getExpires_in());

        verify(userRepository).findByEmail("admin@uberweets.local");
        verify(passwordEncoder).matches("Admin@123", "$we5&345hash");
    }

    @Test
    public void should_not_be_able_to_sign_in_with_wrong_email() {

        var request = new AuthRequestDTO("wrongemail@email.com", "Admin@123");

        when(userRepository.findByEmail("wrongemail@email.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> authService.execute(request));

        verify(userRepository).findByEmail("wrongemail@email.com");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    public void should_not_be_able_to_sign_in_with_wrong_password() {

        var request = new AuthRequestDTO("admin@uberweets.local",  "wrong-password");

        var user =  UserEntity.builder()
                .email("admin@uberweets.local")
                .password_hash("$we5&345hash")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findByEmail("admin@uberweets.local")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "$we5&345hash")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.execute(request));

        verify(userRepository).findByEmail("admin@uberweets.local");
        verify(passwordEncoder).matches("wrong-password", "$we5&345hash");
    }
}

