package com.marcusvynicius.ecommerce_api.product;

import com.marcusvynicius.ecommerce_api.exceptions.ResourceNotFoundException;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductActiveRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.services.SetActiveProductService;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SetActiveProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private SetActiveProductService setActiveProductService;

    @Test
    void should_be_able_to_soft_delete_a_product() {

        var productId = UUID.randomUUID();

        ProductActiveRequestDTO productActiveRequestDTO = ProductActiveRequestDTO.builder()
                .active(false)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Product Active")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.000))
                .active(true)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(productId)
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock_quantity(productEntity.getStock_quantity())
                .active(false)
                .created_at(productEntity.getCreated_at())
                .updated_at(productEntity.getUpdated_at())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.mapToResponseDTO(productEntity)).thenReturn(productResponseDTO);

        var result = setActiveProductService.execute(productId, productActiveRequestDTO);

        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(productId);
        assertEquals(false, result.getActive());
    }

    @Test
    void should_not_be_able_to_soft_delete_a_product_if_not_found() {

        var productId = UUID.randomUUID();

        ProductActiveRequestDTO productActiveRequestDTO = ProductActiveRequestDTO.builder()
                .active(false)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        try {
            setActiveProductService.execute(productId, productActiveRequestDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
        }
    }
}
