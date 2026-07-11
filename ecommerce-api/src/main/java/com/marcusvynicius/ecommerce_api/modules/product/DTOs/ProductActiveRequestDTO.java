package com.marcusvynicius.ecommerce_api.modules.product.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductActiveRequestDTO {

    private Boolean active;
}
