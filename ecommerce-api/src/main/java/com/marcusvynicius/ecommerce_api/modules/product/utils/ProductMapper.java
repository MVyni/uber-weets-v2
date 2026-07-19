package com.marcusvynicius.ecommerce_api.modules.product.utils;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity mapToEntity(ProductRequestDTO productRequestDTO) {
        return ProductEntity.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .stock_quantity(productRequestDTO.getStock_quantity())
                .build();
    }

    public ProductResponseDTO mapToResponseDTO(ProductEntity productEntity) {
        return ProductResponseDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock_quantity(productEntity.getStock_quantity())
                .active(productEntity.isActive())
                .created_at(productEntity.getCreated_at())
                .updated_at(productEntity.getUpdated_at())
                .build();
    }

    public void updateEntityFromRequest(ProductRequestDTO productRequestDTO,  ProductEntity productEntity) {
        productEntity.setName(productRequestDTO.getName());
        productEntity.setDescription(productRequestDTO.getDescription());
        productEntity.setPrice(productRequestDTO.getPrice());
        productEntity.setStock_quantity(productRequestDTO.getStock_quantity());
    }
}
