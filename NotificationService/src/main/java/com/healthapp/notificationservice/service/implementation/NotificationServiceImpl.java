package com.healthapp.notificationservice.service.implementation;

import com.healthapp.notificationservice.entities.Notification;
import com.healthapp.notificationservice.entities.Preference;
import com.healthapp.notificationservice.exceptions.ExternalCallForbiddenException;
import com.healthapp.notificationservice.repository.NotificationRepository;
import com.healthapp.notificationservice.service.interfaces.NotificationService;
import com.healthapp.notificationservice.service.interfaces.PreferenceService;
import com.healthapp.notificationservice.utilities.constants.TokenConstants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PreferenceService preferenceService;
    private final PasswordEncoder passwordEncoder;

    public NotificationServiceImpl(NotificationRepository notificationRepository, PreferenceService preferenceService, PasswordEncoder passwordEncoder) {
        this.notificationRepository = notificationRepository;
        this.preferenceService = preferenceService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new notification for a user.
     *
     * @param userId       The UUID of the user.
     * @param key          The security key for validation.
     * @param notification The notification to create.
     */
    @Override
    public void create(UUID userId, String key, Notification notification) {
        if (!key.equals(TokenConstants.TOKEN_SECRET)) {
            throw new ExternalCallForbiddenException();
        }
        notification.setUserId(userId);
        notification.setTimeCreate(LocalDateTime.now());
        notification.setSeen(false);
        notificationRepository.save(notification);
    }

    /**
     * Get filtered notifications for a user based on preferences.
     *
     * @param userId The UUID of the user.
     * @return List of filtered notifications.
     */
    @Override
    public List<Notification> getFiltredByUserId(UUID userId) {
        // Get user preferences
        Preference userPreference = preferenceService.getByUserId(userId);
        // Get current time
        LocalTime currentTime = LocalTime.now();
        // Filter notifications based on user preferences
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        // Apply preference filters
        if (userPreference.isDoNotDisturb()) {
            notifications.clear();
            return notifications;
        }
        if (userPreference.getMuteFrom() != null && userPreference.getMuteTo() != null) {
            LocalDateTime muteFromDateTime = LocalDateTime.of(LocalDate.now(), userPreference.getMuteFrom());
            LocalDateTime muteToDateTime = LocalDateTime.of(LocalDate.now(), userPreference.getMuteTo());

            notifications.removeIf(notification -> {
                LocalDateTime notificationTime = notification.getTimeCreate();
                return notificationTime.isAfter(muteFromDateTime) && notificationTime.isBefore(muteToDateTime);
            });
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

        notifications.removeIf(Notification::isSeen);

        return notifications;
    }

    /**
     * Mark a notification as "seen" by a user.
     *
     * @param notificationId The UUID of the notification to mark as seen.
     * @param userId         The UUID of the user.
     * @throws IllegalAccessException If the notification is not found or doesn't belong to the user.
     */
    @Override
    public void setSeen(UUID notificationId, UUID userId) throws IllegalAccessException {
        Optional<Notification> notificationOp = notificationRepository.findById(notificationId);
        if (notificationOp.isEmpty()) throw new IllegalAccessException();
        Notification notification = notificationOp.get();
        if (!notification.getUserId().equals(userId)) throw new IllegalAccessException();
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    /**
     * Get all notifications for a user.
     *
     * @param userId The UUID of the user.
     * @return List of all notifications for the user.
     */
    @Override
    public List<Notification> getAllByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    /**
     * Delete a notification by its UUID.
     *
     * @param notificationId The UUID of the notification to delete.
     */
    @Override
    public void delete(UUID notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
