package com.coderdot.seviceImp;

import com.coderdot.dto.SignupRequest;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Customer;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.services.Implemtations.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");

        when(customerRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            savedCustomer.setId(1L);
            return savedCustomer;
        });

        // Act
        Customer result = authService.createCustomer(signupRequest);

        // Assert
        assertNotNull(result);
        assertEquals("hashedPassword", result.getPassword());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateUser() throws IOException {
        // Arrange
        Long userId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("NewFirstName");
        userDTO.setLastname("NewLastName");
        userDTO.setEmail("newemail@example.com");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingCustomer));

        // Act
        Customer updatedUser = authService.updateuser(userId, userDTO);

        // Assert
        assertEquals("NewFirstName", updatedUser.getFirstname());
        assertEquals("NewLastName", updatedUser.getLastname());
        assertEquals("newemail@example.com", updatedUser.getEmail());
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testUpdatePassword_Success() throws IOException {
        // Arrange
        Long userId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setPassword("hashedOldPassword");

        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword("oldPassword");
        updatePassword.setNewPassword("newPassword");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.matches("oldPassword", "hashedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

        // Act
        authService.updatePassword(userId, updatePassword);

        // Assert
        verify(customerRepository, times(1)).save(existingCustomer);
        assertEquals("hashedNewPassword", existingCustomer.getPassword());
    }

    @Test
    void testUpdatePassword_InvalidOldPassword() {
        // Arrange
        Long userId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setPassword("hashedOldPassword");

        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword("wrongPassword");
        updatePassword.setNewPassword("newPassword");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.matches("wrongPassword", "hashedOldPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authService.updatePassword(userId, updatePassword));
    }

    @Test
    void testGetAllUser() {
        // Arrange
        Customer customer = new Customer();
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        // Act
        List<Customer> result = authService.getAllUser();

        // Assert
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        authService.deleteUser(userId);

        // Assert
        verify(customerRepository, times(1)).deleteById(userId);
    }
}
