package com.coderdot.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.coderdot.controllers.UserController1;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Project;
import com.coderdot.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringJUnitConfig // Charge Spring configuration
public class UserController1Test {

    private UserController1 userController;
    private AuthService authServiceMock;

    @BeforeEach
    public void setUp() {
        authServiceMock = mock(AuthService.class);
        userController = new UserController1();
        userController.userService = authServiceMock; // Injection de mock
    }

    @Test
    public void testGetAllUsers() {
        Customer user1 = new Customer(1L, "John", "Doe", "password", "john.doe@example.com", "1234567890","/ss", null, null, null);
        Customer user2 = new Customer(2L, "Jane", "Doe", "password", "jane.doe@example.com", "0987654321","/ss", null, null, null);

        when(authServiceMock.getAllUser()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<Customer>> response = userController.getAllUsers();

        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstname());
        assertEquals("Jane", response.getBody().get(1).getFirstname());
    }

    @Test
    public void testGetUserById() {
        Customer user = new Customer(1L, "John", "Doe", "password", "john.doe@example.com", "1234567890","/ss", null, null, null);

        when(authServiceMock.getUserById(1L)).thenReturn(user);

        ResponseEntity<Customer> response = userController.getUserById(1L);

        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstname());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(authServiceMock).deleteUser(1L);

        ResponseEntity<String> response = userController.deleteUser(1L);

        assertEquals("Utilisateur supprimé avec succès.", response.getBody());
        verify(authServiceMock, times(1)).deleteUser(1L);
    }

    @Test
    public void testUpdateUser() throws IOException {
        Customer updatedUser = new Customer(1L, "John", "Doe", "newpassword", "john.doe@example.com", "1234567890","/ss", null, null, null);
        UserDTO userDTO = new UserDTO(); // Construct your DTO as needed
        when(authServiceMock.updateuser(1L, userDTO)).thenReturn(updatedUser);

        ResponseEntity<Customer> response = userController.updateUser(1L, userDTO);

        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstname());
    }

    @Test
    public void testUpdatePassword() throws IOException {
        UpdatePassword updatePassword = new UpdatePassword("newpassword", "newpassword");
        doNothing().when(authServiceMock).updatePassword(1L, updatePassword);


        ResponseEntity<String> response = userController.updatePassword(1L, updatePassword);

        assertEquals("Mot de passe mis à jour avec succès.", response.getBody());
    }

    @Test
    public void testUpdateImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", "image content".getBytes());
        UserDTO userDTO = new UserDTO(); // Construct the user DTO as needed
        doNothing().when(authServiceMock).updateImage(1L, userDTO);

        ResponseEntity<String> response = userController.updateImage(1L, multipartFile);

        assertEquals("Image mise à jour avec succès", response.getBody());
    }

    @Test
    public void testGetProjectByIdUser() {
        List<Project> projects = Arrays.asList(new Project(1L, "Project1", null,null,null,null), new Project(2L, "Project2",null,null,null,null));
        when(authServiceMock.getProjectUser(1L)).thenReturn(projects);

        ResponseEntity<List<Project>> response = userController.getProjectByIdUser(1L);

        assertEquals(2, response.getBody().size());
        assertEquals("Project1", response.getBody().get(0).getName());
    }
}
