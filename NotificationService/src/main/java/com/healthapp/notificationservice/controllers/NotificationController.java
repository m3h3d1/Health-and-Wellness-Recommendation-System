package com.healthapp.notificationservice.controllers;

import com.healthapp.notificationservice.entities.Notification;
import com.healthapp.notificationservice.service.interfaces.NotificationService;
import com.healthapp.notificationservice.utilities.token.IDExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Create a new notification for a specific user.
     *
     * @param userId The ID of the user for whom the notification is created.
     * @param key key associated with the notification.
     * @param notification The notification object to be created.
     * @return A ResponseEntity with a status message indicating the result of the operation.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<String> createNotification(@PathVariable UUID userId, @RequestParam String key, @RequestBody Notification notification) {
        notificationService.create(userId, key, notification);
        return new ResponseEntity<String>("Notification created", HttpStatus.CREATED);
    }

    /**
     * Get filtered notifications for the current user.
     *
     * @return A list of filtered notifications.
     */
    @GetMapping("/filtered")
    public List<Notification> getFilteredNotifications() {
        return notificationService.getFiltredByUserId(IDExtractor.getUserID());
    }

    /**
     * Get all notifications for the current user.
     *
     * @return A list of all notifications for the current user.
     */
    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllByUserId(IDExtractor.getUserID());
    }

    /**
     * Delete a notification by its ID.
     *
     * @param notificationId The ID of the notification to delete.
     * @return A ResponseEntity with a status message indicating the result of the operation.
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID notificationId) {
        notificationService.delete(notificationId);
        return new ResponseEntity<String>("Notification deleted", HttpStatus.NO_CONTENT);
    }

    /**
     * Set a notification as seen by its ID and the current user.
     *
     * @param notificationId The ID of the notification to mark as seen.
     * @return A ResponseEntity with a status message indicating the result of the operation.
     * @throws IllegalAccessException If there's an issue setting the notification as seen.
     */
    @PutMapping("/set-seen/{notificationId}")
    public ResponseEntity<String> setSeen(@PathVariable UUID notificationId) throws IllegalAccessException {
        notificationService.setSeen(notificationId, IDExtractor.getUserID());
        return new ResponseEntity<String>("Notification updated to seen status", HttpStatus.OK);
    }
}
