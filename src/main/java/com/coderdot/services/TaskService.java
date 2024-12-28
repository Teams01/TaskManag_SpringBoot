package com.coderdot.services;

import com.coderdot.dto.TaskDTO;
import com.coderdot.entities.Task;

import java.util.List;

public interface TaskService {
    public Long addTask(TaskDTO taskDTO);
    public List<Task> getAllTasks();
    public Task getTaskById(Long id);
    public Long updateTask(Long id ,TaskDTO taskDTO);
    public void deleteTask(Long id);

    String updateTaskStatus(Long id,TaskDTO taskDTO);

    String updateTaskPriority(Long id, TaskDTO taskDTO);

    String updateTaskProgress(Long id, TaskDTO taskDTO);

    String updateTaskTD(Long id, TaskDTO taskDTO);
    public String updateTaskStatusTODO(Long id);
    public String updateTaskStatusINPROGRESS(Long id);
    public String updateTaskStatusCOMPLETED(Long id);
    public String updateTaskPriorityLOW(Long id);
    public String updateTaskPriorityMEDIUM(Long id);
    public String updateTaskPriorityHIGH(Long id);



}
