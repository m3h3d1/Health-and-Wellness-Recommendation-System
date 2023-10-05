package com.healthapp.notificationservice.controllers;

import com.healthapp.notificationservice.entities.Notification;
import com.healthapp.notificationservice.service.interfaces.NotificationService;
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

    @PostMapping("/{userId}")
    public ResponseEntity<String> createNotification(@PathVariable UUID userId, @RequestParam String key, @RequestBody Notification notification) {
        notificationService.create(userId, key, notification);
        return new ResponseEntity<String>("Notification created", HttpStatus.CREATED);
    }

    @GetMapping("/filtered/{userId}")
    public List<Notification> getFilteredNotifications(@PathVariable UUID userId) {
        return notificationService.getFiltredByUserId(userId);
    }

    @GetMapping("/all/{userId}")
    public List<Notification> getAllNotifications(@PathVariable UUID userId) {
        return notificationService.getAllByUserId(userId);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID notificationId) {
        notificationService.delete(notificationId);
        return new ResponseEntity<String>("Notification created", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/set-seen/{notificationId}/{userId}")
    public ResponseEntity<String> setSeen(@PathVariable UUID notificationId, @PathVariable UUID userId) throws IllegalAccessException {
        notificationService.setSeen(notificationId, userId);
        return new ResponseEntity<String>("Notification updated to seen status", HttpStatus.OK);
    }
}
