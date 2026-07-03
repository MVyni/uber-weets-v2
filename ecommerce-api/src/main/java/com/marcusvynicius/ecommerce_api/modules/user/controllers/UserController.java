package com.marcusvynicius.ecommerce_api.modules.user.controllers;

import com.marcusvynicius.ecommerce_api.modules.user.DTOs.UserProfileResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.user.services.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User infos")
public class UserController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Get user profile", description = "Get user profile by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserProfileResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    //TO USER AUTH
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(HttpServletRequest request) {

        var userId = request.getAttribute("id");

        var profile = this.userProfileService.execute(UUID.fromString(userId.toString()));
        return ResponseEntity.ok(profile);
    }
}
