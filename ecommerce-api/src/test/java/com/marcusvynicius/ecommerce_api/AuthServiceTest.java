package com.marcusvynicius.ecommerce_api;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void should_be_able_to_sign_in() {

        var login = new AuthRequestDTO("admin@uberweets.local", "Admin@123");

        var user = UserEntity.builder()
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
}

