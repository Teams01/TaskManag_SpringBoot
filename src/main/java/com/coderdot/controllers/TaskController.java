package com.coderdot.controllers;

import com.coderdot.dto.TaskDTO;
import com.coderdot.entities.Task;
import com.coderdot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Ajouter une nouvelle tâche
    @PostMapping("/add")
    public ResponseEntity<Long> addTask(@RequestBody TaskDTO taskDTO) {
        Long taskId = taskService.addTask(taskDTO);
        return ResponseEntity.ok(taskId);
    }

    // Récupérer toutes les tâches
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Récupérer une tâche par ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // Mettre à jour une tâche
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Long updatedTaskId = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTaskId);
    }

    // Supprimer une tâche
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        String updatedTaskId = taskService.updateTaskStatus(id, taskDTO);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updateStatusTODO/{id}")
    public ResponseEntity<String> updateStatusTODO(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskStatusTODO(id);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updateStatusIN_PROGRESS/{id}")
    public ResponseEntity<String> updateStatusINPROGRESS(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskStatusINPROGRESS(id);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updateStatusCOMPLETED/{id}")
    public ResponseEntity<String> updateStatusCOMPLETED(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskStatusCOMPLETED(id);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updatePriorityLOW/{id}")
    public ResponseEntity<String> updatePriorityLOW(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskPriorityLOW(id);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updatePriorityMEDIUM/{id}")
    public ResponseEntity<String> updatePriorityMEDIUM(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskPriorityMEDIUM(id);
        return ResponseEntity.ok(updatedTaskId);
    }
    @PostMapping("/updatePriorityHIGH/{id}")
    public ResponseEntity<String> updatePriorityHIGH(@PathVariable Long id) {
        String updatedTaskId = taskService.updateTaskPriorityHIGH(id);
        return ResponseEntity.ok(updatedTaskId);
    }


}

