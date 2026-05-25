package com.marcusvynicius.ecommerce_api.security;

import com.marcusvynicius.ecommerce_api.providers.JWTUserProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserFilter {

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) {

        //SecurityContextHoler.getContext().setAuthentication(null)
        String header = request.getHeader("Authorization");

        if(request.getRequestURI().startsWith("/user")) {

            if(header != null) {
                var token = this.jwtUserProvider.verifyToken(header);

                if(token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var role = token.getClaim("role").asList(Object.class);
                var grant = role.stream()
                        .map(role -> "ROLE_" + role.toString().toUpperCase())
                        .toList();

                UsarnamePasswordAuthenticationToken auth =
            }
        }
        filterChain.doFilter(request, response);
    }
}
