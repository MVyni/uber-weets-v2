package com.marcusvynicius.ecommerce_api.modules.product.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @Schema(description = "The name of the product", example = "California Flower")
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "The description of the product", example = "A high-quality cannabis flower from California.")
    private String description;

    @Schema(description = "The price of the product", example = "9.99")
    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", message = "Product price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "The quantity of the product in stock", example = "10.200")
    @NotNull(message = "Product stock quantity is required")
    @DecimalMin(value = "0.0", message = "Product stock quantity must be greater than or equal to 0")
    private BigDecimal stock_quantity;
}
