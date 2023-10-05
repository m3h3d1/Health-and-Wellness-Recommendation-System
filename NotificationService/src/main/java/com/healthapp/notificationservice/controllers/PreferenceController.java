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

    /**
     * Get the preference for the current user.
     *
     * @return A ResponseEntity containing the user's preference.
     */
    @GetMapping
    public ResponseEntity<Preference> getPreferenceByUserId() {
        Preference preference = preferenceService.getByUserId(IDExtractor.getUserID());
        return ResponseEntity.ok(preference);
    }

    /**
     * Update the preference for the current user.
     *
     * @param updatedPreference The updated preference object.
     * @return A ResponseEntity with a status message indicating the result of the operation.
     */
    @PostMapping
    public ResponseEntity<String> updatePreference(@RequestBody Preference updatedPreference) {
        preferenceService.update(IDExtractor.getUserID(), updatedPreference);
        return new ResponseEntity<String>("Preference updated", HttpStatus.OK);
    }

    /**
     * Reset the preference for the current user to the default settings.
     *
     * @return A ResponseEntity containing the user's preference after resetting to default.
     */
    @PostMapping("/reset")
    public ResponseEntity<Preference> resetPreferenceToDefault() {
        Preference preference = preferenceService.resetToDefault(IDExtractor.getUserID());
        return ResponseEntity.ok(preference);
    }
}
