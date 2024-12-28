package com.coderdot.services.Implemtations;

import com.coderdot.dto.NotificationDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Notification;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.NotificationRepository;
import com.coderdot.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationImp implements NotificationService {
    @Autowired
    CustomerRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public Long addNotification(NotificationDTO notificationDTO) {
     Customer user = userRepository.findById(notificationDTO.getRecipient()).orElseThrow(()->new IllegalArgumentException("User not found"));
     Notification notification=new Notification();
     notification.setRecipient(user);
     notification.setRead(notificationDTO.isRead());
     notification.setMessage(notificationDTO.getMessage());
     notification.setTimestamp(notificationDTO.getTimestamp());
     notificationRepository.save(notification);
     return notification.getId();
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).get();
    }

    @Override
    public Long updateNotification(Long id, NotificationDTO notificationDTO) {
        Customer user = userRepository.findById(notificationDTO.getRecipient()).orElseThrow(()->new IllegalArgumentException("User not found"));
        Notification notificationToUpdate = notificationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Notification not found"));
        notificationToUpdate.setRecipient(user);
        notificationToUpdate.setRead(notificationDTO.isRead());
        notificationToUpdate.setMessage(notificationDTO.getMessage());
        notificationToUpdate.setTimestamp(notificationDTO.getTimestamp());
        notificationRepository.save(notificationToUpdate);
        return notificationToUpdate.getId();
    }
    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);

    }
}
