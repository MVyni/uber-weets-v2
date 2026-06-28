package com.marcusvynicius.ecommerce_api;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.marcusvynicius.ecommerce_api.providers.JWTProvider;
import com.marcusvynicius.ecommerce_api.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JWTProviderTest {

    private static final String SECRET = "secret-key";

    private JWTProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JWTProvider();
        ReflectionTestUtils.setField(jwtProvider, "secretKey", SECRET);
    }

    @Test
    void should_generate_and_validate_token() {
        UUID userId = UUID.randomUUID();

        String token = TestUtils.generateToken(userId, SECRET);

        DecodedJWT decodedJWT = jwtProvider.verifyToken(token);

        assertNotNull(decodedJWT);
        assertEquals(userId.toString(), decodedJWT.getSubject());
        assertEquals("ADMIN", jwtProvider.getRole(decodedJWT));
        assertNotNull(decodedJWT.getExpiresAtAsInstant());
        assertTrue(decodedJWT.getExpiresAtAsInstant().isAfter(java.time.Instant.now()));
    }

    @Test
    void should_return_null_when_token_signature_is_invalid() {
        UUID userId = UUID.randomUUID();

        String token = TestUtils.generateToken(userId, "wrong-secret");

        DecodedJWT decodedJWT = jwtProvider.verifyToken(token);

        assertNull(decodedJWT);
    }
}

