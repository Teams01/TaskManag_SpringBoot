package com.coderdot.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.coderdot.controllers.TaskController;
import com.coderdot.dto.TaskDTO;
import com.coderdot.entities.Task;
import com.coderdot.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        // Initialize necessary data before each test
    }

    @Test
    public void testAddTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Description");

        when(taskService.addTask(taskDTO)).thenReturn(1L); // Mocking the service call

        ResponseEntity<Long> response = taskController.addTask(taskDTO);

        assertEquals(1L, response.getBody());
        verify(taskService).addTask(taskDTO); // Verify if addTask method was called
    }
    @Test
    public void testGetAllTasks() {
        List<Task> tasks = List.of(new Task(1L, "Test Task 1", "Description 1",1,null,null,null,null,null,null),
                new Task(2L, "Test Task 2", "Description 2",1,null,null,null,null,null,null));
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(2, response.getBody().size());
        assertEquals("Test Task 1", response.getBody().get(0).getTitle());
        verify(taskService).getAllTasks(); // Verify the service call
    }
    @Test
    public void testGetTaskById() {
        Task task =  new Task(2L, "Test Task 2", "Description 2",1,null,null,null,null,null,null);
        when(taskService.getTaskById(1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(1L);

        assertEquals("Test Task", response.getBody().getTitle()); // Utilisez getName() au lieu de getTitle()
        verify(taskService).getTaskById(1L); // Verify the service call
    }

    @Test
    public void testUpdateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");
        taskDTO.setDescription("Updated Description");

        when(taskService.updateTask(1L, taskDTO)).thenReturn(1L); // Mocking the service call

        ResponseEntity<Long> response = taskController.updateTask(1L, taskDTO);

        assertEquals(1L, response.getBody());
        verify(taskService).updateTask(1L, taskDTO); // Verify the service call
    }
    @Test
    public void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L); // Mocking void method

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(204, response.getStatusCodeValue()); // HTTP 204 No Content
        verify(taskService).deleteTask(1L); // Verify the service call
    }
    @Test
    public void testUpdateStatus() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStatus("IN_PROGRESS");

        when(taskService.updateTaskStatus(1L, taskDTO)).thenReturn("Updated Status");

        ResponseEntity<String> response = taskController.updateStatus(1L, taskDTO);

        assertEquals("Updated Status", response.getBody());
        verify(taskService).updateTaskStatus(1L, taskDTO); // Verify the service call
    }



    // Test other methods similarly...
}
