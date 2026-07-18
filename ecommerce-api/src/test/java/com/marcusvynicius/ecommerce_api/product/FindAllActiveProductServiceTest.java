package com.marcusvynicius.ecommerce_api.product;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.services.*;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllActiveProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindAllActiveProductService findAllActiveProductService;

    @Test
    void should_be_able_to_get_all_active_products() {

        Pageable pageable = PageRequest.of(0, 10);

        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product Active")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.00))
                .active(true)
                .build();

        Page<ProductEntity> productPage = new PageImpl<>(
                List.of(productEntity),
                pageable,
                1
        );

        ProductResponseDTO productDTO = ProductResponseDTO.builder()
                .name("Product Active")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.00))
                .active(true)
                .created_at(productEntity.getCreated_at())
                .updated_at(productEntity.getUpdated_at())
                .build();

        when(productRepository.findAllProductByActiveTrue(pageable)).thenReturn(productPage);
        when(productMapper.mapToResponseDTO(productEntity)).thenReturn(productDTO);

        var result = findAllActiveProductService.execute(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Product Active", result.getContent().get(0).getName());
        assertEquals(true, result.getContent().get(0).getActive());
    }
}
