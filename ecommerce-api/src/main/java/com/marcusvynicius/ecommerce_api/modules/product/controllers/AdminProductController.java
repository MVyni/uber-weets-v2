package com.marcusvynicius.ecommerce_api.modules.product.controllers;

import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.product.DTOs.ProductResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.product.services.CreateProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/products")
@Tag(name = "Admin Product", description = "Endpoints for admin managing products")
public class AdminProductController {

    @Autowired
    private CreateProductService createProductService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")

    //SWAGGER DOCUMENTATION
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Product created successfully",
                    content = { @Content(schema =  @Schema(implementation = ProductResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public ResponseEntity<Object> create(@RequestBody @Valid ProductRequestDTO productRequestDTO) {

            var result = createProductService.execute(productRequestDTO);
            return ResponseEntity.status(201).body(result);
    }
}
