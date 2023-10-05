package com.healthapp.notificationservice.controllers;

import com.healthapp.notificationservice.entities.Preference;
import com.healthapp.notificationservice.service.interfaces.PreferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Preference> getPreferenceByUserId(@PathVariable UUID userId) {
        Preference preference = preferenceService.getByUserId(userId);
        return ResponseEntity.ok(preference);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> updatePreference(@PathVariable UUID userId, @RequestBody Preference updatedPreference) {
        preferenceService.update(userId, updatedPreference);
        return new ResponseEntity<String>("Preference updated", HttpStatus.OK);
    }

    @PostMapping("/{userId}/reset")
    public ResponseEntity<Preference> resetPreferenceToDefault(@PathVariable UUID userId) {
        Preference preference = preferenceService.resetToDefault(userId);
        return ResponseEntity.ok(preference);
    }
}
