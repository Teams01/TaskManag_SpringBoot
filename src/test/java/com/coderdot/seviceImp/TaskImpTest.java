package com.coderdot.seviceImp;
import com.coderdot.dto.TaskDTO;
import com.coderdot.entities.*;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.ProjectRepository;
import com.coderdot.repository.TaskRepository;
import com.coderdot.services.Implemtations.TaskImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskImpTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CustomerRepository userRepository;

    @InjectMocks
    private TaskImp taskService;

    private TaskDTO taskDTO;
    private Task task;
    private Project project;
    private Customer user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);
        project.setName("Project Test");

        user = new Customer();
        user.setId(1L);
     user.setLastname("alouan");
     user.setFirstname("ayoub");

        taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Task Description");
        taskDTO.setProjectId(1L);
        taskDTO.setAssignedToId(1L);
        taskDTO.setPriority("HIGH");
        taskDTO.setDueDate(LocalDate.now().plusDays(10));

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Task Description");
        task.setPriority(taskPriority.HIGH);
        task.setStatus(taskStatus.TODO);
        task.setProject(project);
        task.setAssignedTo(user);
    }

    @Test
    void addTask_shouldReturnTaskId() {
        when(projectRepository.findById(taskDTO.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findById(taskDTO.getAssignedToId())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Long taskId = taskService.addTask(taskDTO);

        assertNotNull(taskId);
        assertEquals(1L, taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_shouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task fetchedTask = taskService.getTaskById(1L);

        assertNotNull(fetchedTask);
        assertEquals("Test Task", fetchedTask.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void updateTask_shouldReturnUpdatedTaskId() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.findById(taskDTO.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findById(taskDTO.getAssignedToId())).thenReturn(Optional.of(user));

        // Vérification de la non-nullité de taskDTO.getStatus()
        if (taskDTO.getStatus() != null) {
            task.setStatus(taskStatus.valueOf(taskDTO.getStatus()));
        } else {
            task.setStatus(taskStatus.TODO); // Défaut à TODO si null
        }

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Long updatedTaskId = taskService.updateTask(1L, taskDTO);

        assertNotNull(updatedTaskId);
        assertEquals(1L, updatedTaskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }


    @Test
    void deleteTask_shouldInvokeRepositoryDelete() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateTaskStatus_shouldReturnSuccessMessage() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskDTO.setStatus("IN_PROGRESS");
        String result = taskService.updateTaskStatus(1L, taskDTO);

        assertEquals("Status updated successfully", result);
        assertEquals(taskStatus.IN_PROGRESS, task.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
