package com.marcusvynicius.ecommerce_api.modules.user.controllers;

import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.user.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> auth(@RequestBody AuthRequestDTO authRequestDTO) {

            var authResponseDTO = this.authService.execute(authRequestDTO);
            return ResponseEntity.ok(authResponseDTO);
    }
}
