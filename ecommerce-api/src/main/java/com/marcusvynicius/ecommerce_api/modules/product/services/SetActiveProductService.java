package com.marcusvynicius.ecommerce_api.modules.product.services;

import com.marcusvynicius.ecommerce_api.exceptions.ResourceNotFoundException;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductActiveRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SetActiveProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO execute (UUID productId, ProductActiveRequestDTO productActiveRequestDTO) {
        var active = productActiveRequestDTO.getActive();

        var product = productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Product not found.");
        });

        product.setActive(active);
        var productSaved = productRepository.save(product);

        return productMapper.mapToResponseDTO(productSaved);
    }
}
