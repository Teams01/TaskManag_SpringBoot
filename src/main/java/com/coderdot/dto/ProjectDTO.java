package com.coderdot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private Long owner;

}
