package com.marcusvynicius.ecommerce_api.product;

import com.marcusvynicius.ecommerce_api.modules.product.repositories.ProductRepository;
import com.marcusvynicius.ecommerce_api.modules.user.Role;
import com.marcusvynicius.ecommerce_api.modules.user.UserEntity;
import com.marcusvynicius.ecommerce_api.modules.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.marcusvynicius.ecommerce_api.utils.TestUtils.generateToken;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void should_be_able_costumer_get_all_products() throws Exception {

        var user = UserEntity.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("johndoe@email.com")
                .password_hash("password2344")
                .phone("123456789")
                .role(Role.CUSTOMER)
                .build();

        userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/products/")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .header("Authorization", generateToken(user.getId(), "test-secret")))
                .andExpect(status().isOk());
    }
}
