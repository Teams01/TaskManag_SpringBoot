package com.coderdot.controllers;

import com.coderdot.dto.ProjectDTO;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;
import com.coderdot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Ajouter un nouveau projet
    @PostMapping("/add")
    public ResponseEntity<Long> addProject(@RequestBody ProjectDTO projectDTO) {
        Long projectId = projectService.addProject(projectDTO);
        return ResponseEntity.ok(projectId);
    }

    // Récupérer tous les projets
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Récupérer un projet par ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<Task>> getATaskProjects(@PathVariable Long id) {

        List<Task> task = projectService.getATaskProjects(id);
        return ResponseEntity.ok(task);
    }

    // Mettre à jour un projet
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Long updatedProjectId = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updatedProjectId);
    }

    // Supprimer un projet
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
