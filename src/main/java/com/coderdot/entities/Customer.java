package com.coderdot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String phoneNumber;
    private String image;
    @JsonBackReference
    @OneToMany(mappedBy = "owner")
    private List<Project> projects;

    @JsonBackReference
    @OneToMany(mappedBy = "assignedTo")
    private List<Task> tasks;

    @JsonBackReference
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Customer(long l, String johnDoe, String mail) {
    }
}
