package com.marcusvynicius.ecommerce_api.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    public DecodedJWT verifyToken(String token) {
        token = token.replace("Bearer ", "").trim();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String getRole(DecodedJWT token) {
        var role = token.getClaim("role").asString();

        if (role == null || role.isBlank()) {
            var roles = token.getClaim("roles").asList(String.class);

            if (roles == null || roles.isEmpty()) {
                return null;
            }

            role = roles.get(0);
        }

        return role.trim().toUpperCase();
    }
}
