package com.marcusvynicius.ecommerce_api.modules.product.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String name;

    @Schema(description = "The description of the product", example = "A high-quality cannabis flower from California.")
    private String description;

    @Schema(description = "The price of the product", example = "9.99")
    private BigDecimal price;

    @Schema(description = "The quantity of the product in stock", example = "10.200")
    private BigDecimal stock_quantity;
}
