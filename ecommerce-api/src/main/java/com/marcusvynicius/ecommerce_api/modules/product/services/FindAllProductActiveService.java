package com.marcusvynicius.ecommerce_api.modules.product.services;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllProductActiveService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public Page<ProductResponseDTO> execute() {

        Page<ProductEntity> products = productRepository.findAllByActiveTrue(Pageable.unpaged());
        return productMapper.mapToResponseDTOList(products);
    }
}
