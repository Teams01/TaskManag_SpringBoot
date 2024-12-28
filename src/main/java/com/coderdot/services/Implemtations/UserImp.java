package com.coderdot.services.Implemtations;

import com.coderdot.dto.LogInDTO;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.*;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.UserRepository;
import com.coderdot.services.UserService;
import com.coderdot.response.LogInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@Service
public class UserImp implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private final String storageDirectoryPath = Paths.get("uploaded-images").toAbsolutePath().toString();
    String baseUrl = "http://localhost:8093/uploaded-images/";
    @Override
    public LogInResponse loginUsers(LogInDTO logInDTO) {

        User user = userRepository.findByEmail(logInDTO.getEmail());
        if (user != null) {
            String password = logInDTO.getPassword();
            String encodedPassword = user.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user1 = userRepository.findOneByEmailAndPassword(logInDTO.getEmail(), encodedPassword);
                if (user1.isPresent()) {
                    return new LogInResponse("login Success",true,user.getId());
                }else {
                    return new LogInResponse("login Failed",false,null);
                }
            }else {
                return new LogInResponse("Password not match",false,null);
            }
        }else {
            return new LogInResponse("Email Not Exist",false,null);
        }
    }
    @Override
    public Long adduser(UserDTO userDTO) throws IOException {
        User user = new User();
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setActive(userDTO.isActive());
        user.setRole(role.valueOf(userDTO.getRole()));
        MultipartFile file = userDTO.getImage();
        if (file != null && !file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path storageDirectory = Paths.get(storageDirectoryPath);
            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
            }
            Path destinationPath = storageDirectory.resolve(Path.of(filename));
            file.transferTo(destinationPath);
            user.setImage(baseUrl + filename);
        }
        userRepository.save(user);
        return  user.getId();
    }
    @Override
    public Long updateUser(Long id,UserDTO userDTO) throws IOException {
        User userToUpdate =userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        userToUpdate.setFirstname(userDTO.getFirstname());
        userToUpdate.setLastname(userDTO.getLastname());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        userToUpdate.setActive(userDTO.isActive());
        userToUpdate.setRole(role.valueOf(userDTO.getRole()));
        MultipartFile file = userDTO.getImage();
        if (file != null && !file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path storageDirectory = Paths.get(storageDirectoryPath);
            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
            }
            Path destinationPath = storageDirectory.resolve(Path.of(filename));
            file.transferTo(destinationPath);
            userToUpdate.setImage(baseUrl + filename);
        }
        String newPassword = userDTO.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(userToUpdate);
        return  userToUpdate.getId();
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public void updatePassword(Long id, UpdatePassword updatePassword) throws IOException {
        User userToUpdate = userRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("User not found"));
        if (userToUpdate != null) {
            String password = updatePassword.getOldPassword();
            String encodedPassword = userToUpdate.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if (isPwdRight) {
                userToUpdate.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
                userRepository.save(userToUpdate);
            }
            else  throw new IllegalArgumentException("Old password incorrect");
        }
    }
    @Override
    public void updateImage(Long id, UserDTO userDTO) throws IOException {
        User userToUpdate = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        MultipartFile file = userDTO.getImage();
        if (file != null && !file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path storageDirectory = Paths.get(storageDirectoryPath);
            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
            }
            Path destinationPath = storageDirectory.resolve(Path.of(filename));
            file.transferTo(destinationPath);
            userToUpdate.setImage(baseUrl + filename);
            userRepository.save(userToUpdate);
        }
    }

    @Override
    public List<Project> getProjectUser (Long id){
        Customer user = customerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        return user.getProjects();
    }
    @Override
    public List<Task> getTaskUser (Long id){
        Customer user = customerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        return user.getTasks();
    }
    @Override
    public List<Comment> getCommentUser (Long id){
        Customer user = customerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        return user.getComments();
    }
}
