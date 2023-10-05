package com.healthapp.notificationservice.service.interfaces;
import com.healthapp.notificationservice.entities.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void create(UUID userId, String key, Notification notification);
    List<Notification> getAllByUserId(UUID userId);
    List<Notification> getFiltredByUserId(UUID userId);
    void setSeen(UUID notificationId, UUID userId) throws IllegalAccessException;
    void delete(UUID notificationId);
}