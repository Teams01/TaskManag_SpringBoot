package com.coderdot.services.Implemtations;

import com.coderdot.dto.TaskDTO;
import com.coderdot.entities.*;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.ProjectRepository;
import com.coderdot.repository.TaskRepository;
import com.coderdot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.coderdot.entities.taskPriority.*;
import static com.coderdot.entities.taskStatus.*;

@Service
public class TaskImp implements TaskService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CustomerRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Override
    public Long addTask(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId()).orElseThrow(()->new IllegalArgumentException("Project not found"));
        Customer user =  userRepository.findById(taskDTO.getAssignedToId()).orElseThrow(()->new IllegalArgumentException("User not found"));
        Task task=new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setProgress(0);
        task.setPriority(taskPriority.valueOf(taskDTO.getPriority()));
        task.setStatus(TODO);
        task.setProject(project);
        task.setAssignedTo(user);
        task.setDueDate(taskDTO.getDueDate());
        taskRepository.save(task);
        return task.getId();
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
    @Override
    public Long updateTask(Long taskId, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            // Vérifiez si la statut n'est pas null et convertissez, sinon définissez par défaut
            if (taskDTO.getStatus() != null) {
                task.setStatus(taskStatus.valueOf(taskDTO.getStatus()));
            } else {
                task.setStatus(taskStatus.TODO); // Définir une valeur par défaut
            }

            if (projectRepository.findById(taskDTO.getProjectId()).isPresent()) {
                task.setProject(projectRepository.findById(taskDTO.getProjectId()).get());
            }
            if (userRepository.findById(taskDTO.getAssignedToId()).isPresent()) {
                task.setAssignedTo(userRepository.findById(taskDTO.getAssignedToId()).get());
            }

            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskPriority.valueOf(taskDTO.getPriority()));

            Task updatedTask = taskRepository.save(task);
            return updatedTask.getId();
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    @Override
    public String updateTaskStatus(Long id, TaskDTO taskDTO){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setStatus(taskStatus.valueOf(taskDTO.getStatus()));
        taskRepository.save(taskToUpdate);
        return "Status updated successfully";
    }
    @Override
    public String updateTaskPriority(Long id, TaskDTO taskDTO){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setPriority(taskPriority.valueOf(taskDTO.getPriority() ));
        taskRepository.save(taskToUpdate);
        return "TaskPriority updated successfully";
    }
    @Override
    public String updateTaskProgress(Long id, TaskDTO taskDTO){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setProgress(taskDTO.getProgress());
        taskRepository.save(taskToUpdate);
        return "TaskProgress updated successfully";
    }
    @Override
    public String updateTaskTD(Long id, TaskDTO taskDTO) {
       Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setTitle(taskDTO.getTitle());
        taskToUpdate.setDescription(taskDTO.getDescription());
        taskRepository.save(taskToUpdate);
        return "TaskTD updated successfully";
    }

    @Override
    public String updateTaskStatusTODO(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setStatus(TODO);
        taskRepository.save(taskToUpdate);
        return "Status updated successfully";
    }


    @Override
    public String updateTaskStatusINPROGRESS(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setStatus(IN_PROGRESS);
        taskRepository.save(taskToUpdate);
        return "Status updated successfully";
    }
    @Override
    public String updateTaskStatusCOMPLETED(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setStatus(COMPLETED);
        taskRepository.save(taskToUpdate);
        return "Status updated successfully";
    }
    @Override
    public String updateTaskPriorityLOW(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setPriority(LOW);
        taskRepository.save(taskToUpdate);
        return "TaskPriority updated successfully";
    }
    @Override
    public String updateTaskPriorityMEDIUM(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setPriority(MEDIUM);
        taskRepository.save(taskToUpdate);
        return "TaskPriority updated successfully";
    }
    @Override
    public String updateTaskPriorityHIGH(Long id){
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Task not found"));
        taskToUpdate.setPriority(HIGH);
        taskRepository.save(taskToUpdate);
        return "TaskPriority updated successfully";
    }
}
