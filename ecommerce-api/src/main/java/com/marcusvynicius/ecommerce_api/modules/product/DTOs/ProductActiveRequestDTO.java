package com.marcusvynicius.ecommerce_api.modules.product.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductActiveRequestDTO {

    @NotNull(message = "Product active status is required")
    private Boolean active;
}
