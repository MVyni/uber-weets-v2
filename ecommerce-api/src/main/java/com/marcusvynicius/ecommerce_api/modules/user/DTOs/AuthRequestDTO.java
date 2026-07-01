package com.marcusvynicius.ecommerce_api.modules.user.DTOs;

import lombok.Builder;

@Builder
public record AuthRequestDTO (String email, String password) {
}
