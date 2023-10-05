package com.healthapp.notificationservice.controllers;

import com.healthapp.notificationservice.entities.Preference;
import com.healthapp.notificationservice.service.interfaces.PreferenceService;
import com.healthapp.notificationservice.utilities.token.IDExtractor;
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

    @GetMapping
    public ResponseEntity<Preference> getPreferenceByUserId() {
        Preference preference = preferenceService.getByUserId(IDExtractor.getUserID());
        return ResponseEntity.ok(preference);
    }

    @PostMapping
    public ResponseEntity<String> updatePreference(@RequestBody Preference updatedPreference) {
        preferenceService.update(IDExtractor.getUserID(), updatedPreference);
        return new ResponseEntity<String>("Preference updated", HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<Preference> resetPreferenceToDefault() {
        Preference preference = preferenceService.resetToDefault(IDExtractor.getUserID());
        return ResponseEntity.ok(preference);
    }
}
