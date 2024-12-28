package com.coderdot.services;

import com.coderdot.dto.SignupRequest;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Comment;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;

import java.io.IOException;
import java.util.List;

public interface AuthService {
    Customer createCustomer(SignupRequest signupRequest);
    public Customer updateuser(Long id, UserDTO userDTO) throws IOException;

    List<Customer> getAllUser();

    Customer getUserById(Long id);

    void deleteUser(Long id);

    void updatePassword(Long id, UpdatePassword updatePassword) throws IOException;

    void updateImage(Long id, UserDTO userDTO) throws IOException;

    List<Project> getProjectUser(Long id);

    List<Task> getTaskUser(Long id);

    List<Comment> getCommentUser(Long id);
}
