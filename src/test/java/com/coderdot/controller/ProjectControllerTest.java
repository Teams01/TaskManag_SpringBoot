package com.coderdot.controller;

import com.coderdot.controllers.ProjectController;
import com.coderdot.dto.ProjectDTO;
import com.coderdot.entities.Project;
import com.coderdot.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Test
    public void testGetProjectById() {
        // Given
        Project project = new Project(1L, "Test Project", "Description",null,null,null);
        when(projectService.getProjectById(1L)).thenReturn(project);

        // When
        ResponseEntity<Project> response = projectController.getProjectById(1L);

        // Then
        assertEquals("Test Project", response.getBody().getName());
        verify(projectService).getProjectById(1L); // Verifier l'appel du service
    }
    @Test
    public void testAddProject() {
        // Given
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");

        when(projectService.addProject(projectDTO)).thenReturn(1L);

        // When
        ResponseEntity<Long> response = projectController.addProject(projectDTO);

        // Then
        assertEquals(1L, response.getBody());
        verify(projectService).addProject(projectDTO); // Verifier l'appel du service
    }
    @Test
    public void testUpdateProject() {
        // Given
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");

        when(projectService.updateProject(1L, projectDTO)).thenReturn(1L);

        // When
        ResponseEntity<Long> response = projectController.updateProject(1L, projectDTO);

        // Then
        assertEquals(1L, response.getBody());
        verify(projectService).updateProject(1L, projectDTO); // Verifier l'appel du service
    }
    @Test
    public void testDeleteProject() {
        // Given
        Long projectId = 1L;

        // When
        ResponseEntity<Void> response = projectController.deleteProject(projectId);

        // Then
        assertEquals(204, response.getStatusCodeValue()); // No Content status
        verify(projectService).deleteProject(projectId); // Verifier l'appel du service
    }
}
