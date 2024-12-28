package com.coderdot.controller;

import com.coderdot.controllers.LoginController;
import com.coderdot.dto.LoginRequest;
import com.coderdot.dto.LoginResponse;
import com.coderdot.entities.Customer;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.services.jwt.CustomerServiceImpl;
import com.coderdot.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomerRepository customerRepository;

    /*@Test
    public void testLoginSuccess() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        Customer customer = new Customer(1L, "John", "Doe", "password", "john.doe@example.com", "1234567890","/ss", null, null, null);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUtil.generateToken("test@example.com")).thenReturn("jwtToken");

        // When
        LoginResponse response = loginController.login(loginRequest, null);

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.jwt());
        assertEquals(1L, response.id());
    }*/

    @Test
    public void testLoginBadCredentials() {
        // Given
        LoginRequest loginRequest = new LoginRequest("invalid@example.com", "wrongPassword");
        when(customerRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> loginController.login(loginRequest, null));
    }

   /* @Test
    public void testLoginDisabledAccount() throws IOException {
        // Given
        LoginRequest loginRequest = new LoginRequest("disabled@example.com", "password");
        Customer customer = new Customer(1L, "John", "Doe", "password", "john.doe@example.com", "1234567890","/ss", null, null, null);
        when(customerRepository.findByEmail("disabled@example.com")).thenReturn(Optional.of(customer));

        // When & Then
        assertThrows(DisabledException.class, () -> loginController.login(loginRequest, mock(HttpServletResponse.class)));
    }*/
}
