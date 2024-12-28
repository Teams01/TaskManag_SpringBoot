package com.coderdot.services.Implemtations;

import com.coderdot.dto.ProjectDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;
import com.coderdot.entities.projectStatut;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.ProjectRepository;
import com.coderdot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.coderdot.entities.projectStatut.IN_PROGRESS;

@Service
public class ProjectImp implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CustomerRepository userRepository;
    @Override
    public Long addProject(ProjectDTO projectDTO) {
        Customer user = userRepository.findById(projectDTO.getOwner()).orElseThrow(() ->new IllegalArgumentException("User not found"));
        Project project=new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(IN_PROGRESS);
        project.setOwner(user);
        projectRepository.save(project);
        return project.getId();
    }
    @Override
    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }
    @Override
    public Project getProjectById(Long id) {

        return projectRepository.findById(id).get();
    }
    @Override
    public Long updateProject(Long id, ProjectDTO projectDTO) {
        Customer user = userRepository.findById(projectDTO.getOwner()).orElseThrow(() ->new IllegalArgumentException("User not found"));
        Project projectToUpdate = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Project not found"));
        projectToUpdate.setName(projectDTO.getName());
        projectToUpdate.setDescription(projectDTO.getDescription());
        projectToUpdate.setStatus(projectStatut.valueOf(projectDTO.getStatus()));
        projectToUpdate.setOwner(user);
        projectRepository.save(projectToUpdate);
        return projectToUpdate.getId();
    }
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    @Override
    public String updateProjectStatus(Long id, ProjectDTO projectDTO) {
        Project projectToUpdate = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Project not found"));
        projectToUpdate.setStatus(projectStatut.valueOf(projectDTO.getStatus()));
        projectRepository.save(projectToUpdate);
        return "ProjectStatus updated successfully";
    }
    @Override
    public String updateProjectND(Long id, ProjectDTO projectDTO) {
        Project projectToUpdate = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Project not found"));
        projectToUpdate.setName(projectDTO.getName());
        projectToUpdate.setDescription(projectDTO.getDescription());
        projectRepository.save(projectToUpdate);
        return "ProjectND updated successfully";
    }
    @Override
    public List<Task> getATaskProjects(Long id ) {
        Project project = getProjectById(id);
        return  project.getTasks();
    }
}
