package com.coderdot.services;

import com.coderdot.dto.LogInDTO;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Comment;
import com.coderdot.entities.Project;
import com.coderdot.entities.Task;
import com.coderdot.entities.User;
import com.coderdot.response.LogInResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public LogInResponse loginUsers(LogInDTO logInDTO);
    public Long adduser(UserDTO userDTO)throws IOException;
    public List<User> getAllUser();
    public User getUserById(Long id);
    public void deleteUser(Long id);
    public void updatePassword(Long id, UpdatePassword updatePassword) throws IOException;
    public void updateImage(Long id,UserDTO userDTO) throws IOException;
    public Long updateUser(Long id,UserDTO userDTO) throws IOException;
    public List<Project> getProjectUser (Long id);
    public List<Task> getTaskUser (Long id);
    public List<Comment> getCommentUser (Long id);
}
