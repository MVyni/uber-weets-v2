package com.marcusvynicius.ecommerce_api.modules.product.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    @Schema(description = "The unique identifier of the product", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "The name of the product", example = "California Flower")
    private String name;

    @Schema(description = "The description of the product", example = "A high-quality cannabis flower from California.")
    private String description;

    @Schema(description = "The price of the product", example = "9.99")
    private BigDecimal price;

    @Schema(description = "The quantity of the product in stock", example = "10.200")
    private BigDecimal stock_quantity;

    @Schema(description = "Indicates if the product is active", example = "true")
    private Boolean active;

    @Schema(description = "The date and time when the product was created", example = "2023-01-01T12:00:00")
    private LocalDateTime created_at;

    @Schema(description = "The date and time when the product was last updated", example = "2023-01-01T12:00:00")
    private LocalDateTime updated_at;
}
