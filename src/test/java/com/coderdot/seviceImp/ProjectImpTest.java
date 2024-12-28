package com.coderdot.seviceImp;


import com.coderdot.dto.ProjectDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;
import com.coderdot.entities.projectStatut;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.ProjectRepository;
import com.coderdot.services.Implemtations.ProjectImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.coderdot.entities.projectStatut.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectImpTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CustomerRepository userRepository;

    @InjectMocks
    private ProjectImp projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void addProject_ShouldAddProjectAndReturnId() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setOwner(1L);
        projectDTO.setName("Project Name");
        projectDTO.setDescription("Project Description");

        Customer user = new Customer();
        user.setId(1L);

        Project project = new Project();
        project.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Long projectId = projectService.addProject(projectDTO);

        assertNotNull(projectId);
        assertEquals(10L, projectId);
        verify(projectRepository, times(1)).save(any(Project.class));
    }*/

    @Test
    void getAllProjects_ShouldReturnAllProjects() {
        Project project1 = new Project();
        Project project2 = new Project();

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void getProjectById_ShouldReturnProject() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project retrievedProject = projectService.getProjectById(1L);

        assertNotNull(retrievedProject);
        assertEquals(1L, retrievedProject.getId());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void updateProject_ShouldUpdateProjectAndReturnId() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setOwner(1L);
        projectDTO.setName("Updated Project Name");
        projectDTO.setDescription("Updated Project Description");
        projectDTO.setStatus("DONE");

        Customer user = new Customer();
        user.setId(1L);

        Project existingProject = new Project();
        existingProject.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(3L)).thenReturn(Optional.of(existingProject));

        Long updatedId = projectService.updateProject(3L, projectDTO);

        assertNotNull(updatedId);
        assertEquals(3L, updatedId);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    void deleteProject_ShouldDeleteProject() {
        Long projectId = 5L;

        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void updateProjectStatus_ShouldUpdateStatusSuccessfully() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setStatus("DONE");

        Project project = new Project();
        project.setId(4L);
        project.setStatus(IN_PROGRESS);

        when(projectRepository.findById(4L)).thenReturn(Optional.of(project));

        String response = projectService.updateProjectStatus(4L, projectDTO);

        assertEquals("ProjectStatus updated successfully", response);
        assertEquals(projectStatut.DONE, project.getStatus());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void getATaskProjects_ShouldReturnTasksOfProject() {
        Task task1 = new Task();
        Task task2 = new Task();

        Project project = new Project();
        project.setId(6L);
        project.setTasks(Arrays.asList(task1, task2));

        when(projectRepository.findById(6L)).thenReturn(Optional.of(project));

        List<Task> tasks = projectService.getATaskProjects(6L);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(projectRepository, times(1)).findById(6L);
    }
}
