package com.marcusvynicius.ecommerce_api.modules.user.services;

import com.marcusvynicius.ecommerce_api.exceptions.ResourceNotFoundException;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.UserProfileResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    UserRepository userRepository;

    public UserProfileResponseDTO execute(UUID userId) {

        var user =  userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
