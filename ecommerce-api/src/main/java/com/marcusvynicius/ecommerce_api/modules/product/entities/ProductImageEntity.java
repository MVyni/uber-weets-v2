package com.marcusvynicius.ecommerce_api.modules.product.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_images")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private ProductEntity product;

    @NotBlank
    @Schema(description = "Product image URL", example = "https://example.com/images/california-love-front.jpg")
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Schema(description = "Alternative text for the product image", example = "Front image of California Love")
    @Column(name = "alt_text")
    private String altText;

    @Schema(description = "Indicates whether this is the main image of the product", example = "true")
    @Builder.Default
    @Column(name = "is_main_image", nullable = false)
    private boolean mainImage = false;

    @NotNull
    @Schema(description = "Display order of the product image", example = "0")
    @Builder.Default
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
