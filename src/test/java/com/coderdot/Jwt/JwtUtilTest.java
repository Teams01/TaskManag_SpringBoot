package com.coderdot.Jwt;

import static org.junit.jupiter.api.Assertions.*;

import com.coderdot.repository.CustomerRepository;
import com.coderdot.services.jwt.CustomerServiceImpl;
import com.coderdot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

class JwtUtilTest {
    @Autowired
    CustomerRepository customerRepository;
    private final JwtUtil jwtUtil = new JwtUtil();
    private final CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository); // Inject your CustomerRepository


    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token, "Token should not be null");
        assertTrue(token.startsWith("eyJ"), "Token should be a valid JWT");
    }

    @Test
    void testExtractUsername() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername, "Extracted username should match the original username");
    }

    @Test
    void testTokenExpiration() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        Date expirationDate = jwtUtil.extractExpiration(token);

        assertNotNull(expirationDate, "Expiration date should not be null");
        assertTrue(expirationDate.after(new Date()), "Token should not be expired immediately after generation");
    }
/*
    @Test
    void testValidateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        UserDetails userDetails = customerService.loadUserByUsername(username);

        boolean isValid = jwtUtil.validateToken(token, userDetails);

        assertTrue(isValid, "Token should be valid with correct username and not expired");
    }

   @Test
    void testValidateExpiredToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Simuler un token expiré en manipulant la date d'expiration
        Claims claims = jwtUtil.extractAllClaims(token);
        claims.setExpiration(new Date(System.currentTimeMillis() - 1000L * 60)); // -1 minute pour simuler l'expiration

        boolean isExpiredValid = jwtUtil.validateToken(token, customerService.loadUserByUsername(username));

        assertFalse(isExpiredValid, "Token should be invalid if expired");
    }
   */
}
