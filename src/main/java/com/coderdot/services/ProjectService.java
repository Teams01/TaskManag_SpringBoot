package com.coderdot.services;

import com.coderdot.dto.ProjectDTO;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;

import java.util.List;

public interface ProjectService {
    public Long addProject(ProjectDTO projectDTO);
    public List<Project> getAllProjects();
    public Project getProjectById(Long id);
    public Long updateProject(Long id ,ProjectDTO projectDTO);
    public void deleteProject(Long id);

    String updateProjectStatus(Long id, ProjectDTO projectDTO);

    String updateProjectND(Long id, ProjectDTO projectDTO);
    List<Task> getATaskProjects(Long id );
}
