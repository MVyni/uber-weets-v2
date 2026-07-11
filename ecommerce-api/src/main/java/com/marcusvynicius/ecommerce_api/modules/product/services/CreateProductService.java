package com.marcusvynicius.ecommerce_api.modules.product.services;

import com.marcusvynicius.ecommerce_api.exceptions.BusinessException;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO execute(ProductRequestDTO productRequestDTO) {

        if(productRequestDTO.getName() == null || productRequestDTO.getName().isEmpty()) {
            throw new BusinessException("Product name is required");
        }

        if(productRequestDTO.getPrice() == null || productRequestDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Product price must be greater than zero");
        }

        if(productRequestDTO.getStock_quantity() == null || productRequestDTO.getStock_quantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Product stock quantity must be greater than zero");
        }

        var productEntity = productMapper.mapToEntity(productRequestDTO);
        var productSaved = productRepository.save(productEntity);

        return productMapper.mapToResponseDTO(productSaved);
    }
}
