package com.marcusvynicius.ecommerce_api.product;

import com.marcusvynicius.ecommerce_api.exceptions.ResourceNotFoundException;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductEntity;
import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.product.services.FindProductByIdService;
import com.marcusvynicius.ecommerce_api.modules.product.utils.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindProductByIdServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindProductByIdService findProductByIdService;

    @Test
    void should_be_able_to_find_product_by_id() {

        var productId  = UUID.randomUUID();

        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Product Active")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.00))
                .active(true)
                .build();

        ProductResponseDTO productDTO = ProductResponseDTO.builder()
                .id(productId)
                .name("Product Active")
                .description("Description")
                .price(BigDecimal.valueOf(10.00))
                .stock_quantity(BigDecimal.valueOf(5.00))
                .active(true)
                .created_at(productEntity.getCreated_at())
                .updated_at(productEntity.getUpdated_at())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.mapToResponseDTO(productEntity)).thenReturn(productDTO);

        var result = findProductByIdService.execute(productId);

        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(productId);
    }

    @Test
    void should_not_be_able_to_find_product_by_id_if_not_found() {

        var productId  = UUID.randomUUID();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        try {
            findProductByIdService.execute(productId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
        }
    }
}
