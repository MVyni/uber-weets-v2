package com.marcusvynicius.ecommerce_api.modules.user.DTOs;

import com.marcusvynicius.ecommerce_api.modules.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private boolean active;
}
