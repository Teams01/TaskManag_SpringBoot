package com.coderdot;



import com.coderdot.dto.SignupRequest;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Customer;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.services.AuthService;
import com.coderdot.services.Implemtations.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer_Success() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password");

        when(customerRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("hashedPassword");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setEmail(signupRequest.getEmail());
        savedCustomer.setPassword("hashedPassword");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = authService.createCustomer(signupRequest);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        assertEquals(1L, result.getId());
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");

        when(customerRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        Customer result = authService.createCustomer(signupRequest);

        assertNull(result);
    }

    @Test
    void testUpdatePassword_Success() throws IOException {
        Long userId = 1L;
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword("oldPassword");
        updatePassword.setNewPassword("newPassword");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(userId);
        existingCustomer.setPassword("encodedOldPassword");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.matches(updatePassword.getOldPassword(), "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode(updatePassword.getNewPassword())).thenReturn("encodedNewPassword");

        authService.updatePassword(userId, updatePassword);

        verify(customerRepository, times(1)).save(existingCustomer);
        assertEquals("encodedNewPassword", existingCustomer.getPassword());
    }

    @Test
    void testUpdatePassword_InvalidOldPassword() {
        Long userId = 1L;
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword("wrongPassword");
        updatePassword.setNewPassword("newPassword");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(userId);
        existingCustomer.setPassword("encodedOldPassword");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.matches(updatePassword.getOldPassword(), "encodedOldPassword")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.updatePassword(userId, updatePassword);
        });

        assertEquals("Old password incorrect", exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        Long userId = 1L;
        Customer customer = new Customer();
        customer.setId(userId);

        when(customerRepository.findById(userId)).thenReturn(Optional.of(customer));

        Customer result = authService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

}
