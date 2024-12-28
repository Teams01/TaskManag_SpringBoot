package com.coderdot.seviceImp;



import com.coderdot.dto.NotificationDTO;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Notification;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.NotificationRepository;
import com.coderdot.services.Implemtations.NotificationImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationImpTest {

    @Mock
    private CustomerRepository userRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationImp notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNotification_ShouldAddNotificationAndReturnId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRecipient(1L);
        notificationDTO.setMessage("Test notification");
        notificationDTO.setRead(false);
        notificationDTO.setTimestamp(LocalDate.now());

        Customer user = new Customer();
        user.setId(1L);

        Notification notification = new Notification();
        notification.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Long notificationId = notificationService.addNotification(notificationDTO);

        assertNotNull(notificationId);
        assertEquals(10L, notificationId);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void getAllNotifications_ShouldReturnAllNotifications() {
        Notification notification1 = new Notification();
        Notification notification2 = new Notification();

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

        List<Notification> notifications = notificationService.getAllNotifications();

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void getNotificationById_ShouldReturnNotification() {
        Notification notification = new Notification();
        notification.setId(1L);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Notification retrievedNotification = notificationService.getNotificationById(1L);

        assertNotNull(retrievedNotification);
        assertEquals(1L, retrievedNotification.getId());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void updateNotification_ShouldUpdateNotificationAndReturnId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRecipient(1L);
        notificationDTO.setMessage("Updated notification");
        notificationDTO.setRead(true);
        notificationDTO.setTimestamp(LocalDate.now());

        Customer user = new Customer();
        user.setId(1L);

        Notification existingNotification = new Notification();
        existingNotification.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.findById(3L)).thenReturn(Optional.of(existingNotification));

        Long updatedId = notificationService.updateNotification(3L, notificationDTO);

        assertNotNull(updatedId);
        assertEquals(3L, updatedId);
        verify(notificationRepository, times(1)).save(existingNotification);
    }

    @Test
    void deleteNotification_ShouldDeleteNotification() {
        Long notificationId = 5L;

        doNothing().when(notificationRepository).deleteById(notificationId);

        notificationService.deleteNotification(notificationId);

        verify(notificationRepository, times(1)).deleteById(notificationId);
    }
}
