package com.coderdot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private int progress;
    private String priority;
    private String status;
    private Date dueDate;
    private Long projectId; // ID du projet associé
    private Long assignedToId; // ID de l'utilisateur assigné

}
