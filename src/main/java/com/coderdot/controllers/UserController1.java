package com.coderdot.controllers;

import com.coderdot.dto.UpdatePassword;
import com.coderdot.dto.UserDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Project;
import com.coderdot.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users1")
public class UserController1 {
    @Autowired
    private AuthService userService;

    /**
     * Authentification d'un utilisateur.
     */


    /**
     * Récupérer tous les utilisateurs.
     */
    @GetMapping(path="/all")
    public ResponseEntity<List<Customer>> getAllUsers() {
        List<Customer> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    /**
     * Récupérer un utilisateur par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getUserById(@PathVariable Long id) {
        Customer user = userService.getUserById(id);
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
    public ResponseEntity<Customer> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws IOException {
        Customer updatedUser = userService.updateuser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
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
    @PostMapping("/image/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            // Crée un DTO pour transporter l'image
            UserDTO employeeDTO = new UserDTO();
            employeeDTO.setImage(imageFile);

            // Appelle le service pour mettre à jour l'image
            userService.updateImage(id, employeeDTO);
            return ResponseEntity.ok("Image mise à jour avec succès");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'image");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
        }
    }

    @GetMapping("/getProject/{id}")
    public ResponseEntity<List<Project>> getProjectByIdUser(@PathVariable Long id) {
       List<Project> project = userService.getProjectUser(id);
        return ResponseEntity.ok(project);
    }
}
