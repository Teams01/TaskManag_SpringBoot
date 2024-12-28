package com.coderdot.services;

import com.coderdot.dto.NotificationDTO;
import com.coderdot.entities.Notification;

import java.util.List;

public interface NotificationService {
    public Long addNotification(NotificationDTO notificationDTO);
    public List<Notification> getAllNotifications();
    public Notification getNotificationById(Long id);
    public Long updateNotification(Long id ,NotificationDTO notificationDTO);
    public void deleteNotification(Long id);
}
