package com.marcusvynicius.ecommerce_api.modules.user.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marcusvynicius.ecommerce_api.exceptions.InvalidCredentialsException;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO execute(AuthRequestDTO authRequestDTO) {

        var user = this.userRepository.findByEmail(authRequestDTO.email())
                .orElseThrow(InvalidCredentialsException::new);

        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.password(), user.getPassword_hash());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token =  JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(java.util.Date.from(expiresIn))
                .sign(algorithm);

        return AuthResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }
}
