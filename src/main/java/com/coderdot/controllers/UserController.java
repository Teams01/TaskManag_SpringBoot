package com.coderdot.controllers;

import com.coderdot.dto.LogInDTO;
import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.User;
import com.coderdot.services.UserService;
import com.coderdot.response.LogInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Authentification d'un utilisateur.
     */
    @PostMapping("/login")
    public ResponseEntity<LogInResponse> loginUser(@RequestBody LogInDTO logInDTO) {
        LogInResponse response = userService.loginUsers(logInDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Ajouter un nouvel utilisateur.
     */
    @PostMapping("/add")
    public ResponseEntity<Long> addUser(@ModelAttribute UserDTO userDTO) throws IOException {
        Long userId = userService.adduser(userDTO);
        return ResponseEntity.ok(userId);
    }

    /**
     * Récupérer tous les utilisateurs.
     */
    @GetMapping(path="/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    /**
     * Récupérer un utilisateur par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Supprimer un utilisateur par ID.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès.");
    }

    /**
     * Mettre à jour un utilisateur.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO) throws IOException {
        Long updatedUserId = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUserId);
    }

    /**
     * Mettre à jour le mot de passe de l'utilisateur.
     */
    @PutMapping("/update-password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UpdatePassword updatePassword) throws IOException {
        userService.updatePassword(id, updatePassword);
        return ResponseEntity.ok("Mot de passe mis à jour avec succès.");
    }

    /**
     * Mettre à jour l'image d'un utilisateur.
     */
    @PutMapping("/update-image/{id}")
    public ResponseEntity<String> updateImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setImage(image);
        userService.updateImage(id, userDTO);
        return ResponseEntity.ok("Image mise à jour avec succès.");
    }
}
