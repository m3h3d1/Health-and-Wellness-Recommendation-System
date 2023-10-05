package com.healthapp.notificationservice.service.implementation;

import com.healthapp.notificationservice.TokenConstants;
import com.healthapp.notificationservice.entities.Notification;
import com.healthapp.notificationservice.entities.Preference;
import com.healthapp.notificationservice.repository.NotificationRepository;
import com.healthapp.notificationservice.service.interfaces.NotificationService;
import com.healthapp.notificationservice.service.interfaces.PreferenceService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PreferenceService preferenceService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, PreferenceService preferenceService) {
        this.notificationRepository = notificationRepository;
        this.preferenceService = preferenceService;
    }

    @Override
    public void create(UUID userId, String key, Notification notification) {
        if(!key.equals(TokenConstants.TOKEN_SECRET)){
            return;
        }
        notification.setUserId(userId);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllByUserId(UUID userId) {
        // Get user preferences
        Preference userPreference = preferenceService.getByUserId(userId);
        // Get current time
        LocalTime currentTime = LocalTime.now();
        // Filter notifications based on user preferences
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        // Apply preference filters
        if (userPreference.isDoNotDisturb()) {
            notifications.clear();
        }
        if (!userPreference.isGetPostInteractionNotification()) {
            notifications.removeIf(notification -> notification.getType().equals("post_interaction"));
        }
        if (!userPreference.isGetConnectionInteractionNotification()) {
            notifications.removeIf(notification -> notification.getType().equals("connection_interaction"));
        }

        if (!userPreference.isGetHealthFeedNotification()) {
            notifications.removeIf(notification -> notification.getType().equals("health_feed"));
        }

        if (!userPreference.isGetRecommendationNotification()) {
            notifications.removeIf(notification -> notification.getType().equals("recommendation"));
        }

        if (!userPreference.isGetAccountNotification()) {
            notifications.removeIf(notification -> notification.getType().equals("account"));
        }

        return notifications;
    }

    @Override
    public void delete(UUID notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
