package com.marcusvynicius.ecommerce_api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthRequestDTO;
import com.marcusvynicius.ecommerce_api.modules.user.DTOs.AuthResponseDTO;
import com.marcusvynicius.ecommerce_api.modules.user.Role;
import com.marcusvynicius.ecommerce_api.modules.user.UserEntity;
import com.marcusvynicius.ecommerce_api.modules.user.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.marcusvynicius.ecommerce_api.utils.TestUtils.objectToJSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthUserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void should_be_able_to_sign_in() throws Exception {

        var user = UserEntity.builder()
                .name("admin")
                .email("admin@email.com")
                .password_hash(passwordEncoder.encode("admin1234"))
                .role(Role.valueOf("ADMIN"))
                .build();

        userRepository.saveAndFlush(user);

        var authRequestDTO = AuthRequestDTO.builder()
                .email("admin@email.com")
                .password("admin1234")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(authRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_not_be_able_to_sign_in_with_wrong_password() throws Exception {

        var user = UserEntity.builder()
                .name("admin")
                .email("admin@email.com")
                .password_hash(passwordEncoder.encode("admin1234"))
                .role(Role.valueOf("ADMIN"))
                .build();

        userRepository.saveAndFlush(user);

        var authRequestDTO = AuthRequestDTO.builder()
                .email("admin@email.com")
                .password("admin123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(authRequestDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_be_able_to_get_user_profile() throws Exception {

        var user = UserEntity.builder()
                .name("admin")
                .email("profile@email.com")
                .password_hash(passwordEncoder.encode("admin1234"))
                .role(Role.ADMIN)
                .build();

        userRepository.saveAndFlush(user);

        // Login to get a real JWT token
        var authRequestDTO = AuthRequestDTO.builder()
                .email("profile@email.com")
                .password("admin1234")
                .build();

        var loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(authRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        // Extract access_token from login response
        var responseBody = loginResult.getResponse().getContentAsString();
        var authResponse = objectMapper.readValue(responseBody, AuthResponseDTO.class);

        // Use the real token to access the protected profile endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")
                .header("Authorization", "Bearer " + authResponse.getAccess_token()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_not_be_able_to_get_user_profile_with_invalid_token() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }
}
