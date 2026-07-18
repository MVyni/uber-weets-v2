package com.marcusvynicius.ecommerce_api.modules.product.controllers;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.services.FindAllActiveProductService;
import com.marcusvynicius.ecommerce_api.modules.product.services.FindProductByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Endpoints for managing products")
public class ProductController {

    @Autowired
    private FindAllActiveProductService findAllActiveProductService;

    @Autowired
    private FindProductByIdService findProductByIdService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")

    //SWAGGER DOCUMENTATION
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                    content = { @Content(schema =  @Schema(implementation = ProductResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Object> getProductById(@PathVariable("id") UUID productId) {

        var result = findProductByIdService.execute(productId);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")

    //SWAGGER DOCUMENTATION
    @Operation(summary = "Get all active products", description = "Retrieve a paginated list of all active products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = { @Content(schema =  @Schema(implementation = ProductResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<Object> getAllActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        var result = findAllActiveProductService.execute(pageable);

        return ResponseEntity.status(200).body(result);
    }
}
