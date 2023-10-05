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

    @PostMapping("/{userId}")
    public ResponseEntity<String> createNotification(@PathVariable UUID userId, @RequestParam String key, @RequestBody Notification notification) {
        notificationService.create(userId, key, notification);
        return new ResponseEntity<String>("Notification created", HttpStatus.CREATED);
    }

    @GetMapping("/filtered")
    public List<Notification> getFilteredNotifications() {
        return notificationService.getFiltredByUserId(IDExtractor.getUserID());
    }

    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllByUserId(IDExtractor.getUserID());
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID notificationId) {
        notificationService.delete(notificationId);
        return new ResponseEntity<String>("Notification created", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/set-seen/{notificationId}")
    public ResponseEntity<String> setSeen(@PathVariable UUID notificationId) throws IllegalAccessException {
        notificationService.setSeen(notificationId, IDExtractor.getUserID());
        return new ResponseEntity<String>("Notification updated to seen status", HttpStatus.OK);
    }
}
