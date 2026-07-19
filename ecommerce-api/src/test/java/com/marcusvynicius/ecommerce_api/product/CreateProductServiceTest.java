package com.marcusvynicius.ecommerce_api.product;

import com.marcusvynicius.ecommerce_api.exceptions.BusinessException;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.services.CreateProductService;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void should_be_able_to_create_a_new_product() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("New Product")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.000))
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("New Product")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.000))
                .active(true)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(productEntity.getId())
                .name("New Product")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.000))
                .active(true)
                .created_at(productEntity.getCreated_at())
                .updated_at(productEntity.getUpdated_at())
                .build();

        when(productMapper.mapToEntity(productRequestDTO)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.mapToResponseDTO(productEntity)).thenReturn(productResponseDTO);

        var result = createProductService.execute(productRequestDTO);

        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(productEntity.getId());
        assertEquals("New Product", result.getName());
        assertEquals(result, productResponseDTO);
    }

    @Test
    void should_not_be_able_to_create_a_product_with_invalid_data() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("")
                .description("Description")
                .price(BigDecimal.valueOf(-10.00))
                .stock_quantity(BigDecimal.valueOf(-5.000))
                .build();

        try {
            createProductService.execute(productRequestDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
        }
    }
}
