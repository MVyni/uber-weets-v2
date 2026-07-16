package com.marcusvynicius.ecommerce_api.modules.product.services;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductByIdService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO execute(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.mapToResponseDTO(product);
    }
}
