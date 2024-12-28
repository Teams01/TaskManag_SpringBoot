package com.coderdot.controllers;

import com.coderdot.dto.NotificationDTO;
import com.coderdot.entities.Notification;
import com.coderdot.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Ajouter une notification
    @PostMapping("/add")
    public ResponseEntity<Long> addNotification(@RequestBody NotificationDTO notificationDTO) {
        Long notificationId = notificationService.addNotification(notificationDTO);
        return ResponseEntity.ok(notificationId);
    }

    // Récupérer toutes les notifications
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Récupérer une notification par ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    // Mettre à jour une notification
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        Long updatedNotificationId = notificationService.updateNotification(id, notificationDTO);
        return ResponseEntity.ok(updatedNotificationId);
    }

    // Supprimer une notification
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
