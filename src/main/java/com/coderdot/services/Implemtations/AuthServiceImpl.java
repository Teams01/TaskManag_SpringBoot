package com.coderdot.services.Implemtations;

import com.coderdot.dto.SignupRequest;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.*;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.services.AuthService;
import org.springframework.beans.BeanUtils;
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

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final String storageDirectoryPath = Paths.get("uploaded-images").toAbsolutePath().toString();
    String baseUrl = "http://localhost:8080/uploaded-images/";
    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Customer createCustomer(SignupRequest signupRequest) {
        //Check if customer already exist
        if (customerRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(signupRequest,customer);
        //Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashPassword);
        Customer createdCustomer = customerRepository.save(customer);
        customer.setId(createdCustomer.getId());
        return customer;
    }
    @Override
    public Customer updateuser(Long id, UserDTO userDTO) throws IOException {
        Customer userToUpdate =customerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        userToUpdate.setFirstname(userDTO.getFirstname());
        userToUpdate.setLastname(userDTO.getLastname());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        customerRepository.save(userToUpdate);
        return userToUpdate;
    }
    @Override
    public List<Customer> getAllUser() {

        return customerRepository.findAll();
    }
    @Override
    public Customer getUserById(Long id) {

        return customerRepository.findById(id).get();
    }
    @Override
    public void deleteUser(Long id) {
        customerRepository.deleteById(id);

    }
    @Override
    public void updatePassword(Long id, UpdatePassword updatePassword) throws IOException {
        Customer userToUpdate = customerRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("User not found"));
        if (userToUpdate != null) {
            String password = updatePassword.getOldPassword();
            String encodedPassword = userToUpdate.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                userToUpdate.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
                customerRepository.save(userToUpdate);
            }
            else  throw new IllegalArgumentException("Old password incorrect");
        }
    }
    @Override
    public void updateImage(Long id, UserDTO userDTO) throws IOException {
        Customer userToUpdate = customerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
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
            customerRepository.save(userToUpdate);
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
