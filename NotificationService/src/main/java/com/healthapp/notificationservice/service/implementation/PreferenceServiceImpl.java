package com.healthapp.notificationservice.service.implementation;

import com.healthapp.notificationservice.entities.Preference;
import com.healthapp.notificationservice.repository.PreferenceRepository;
import com.healthapp.notificationservice.service.interfaces.PreferenceService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * Get user preferences by user ID. If preferences don't exist, create default preferences.
     *
     * @param userId The UUID of the user.
     * @return The user's preferences.
     */
    @Override
    public Preference getByUserId(UUID userId) {
        Optional<Preference> preferenceOp = preferenceRepository.findByUserId(userId);
        if (preferenceOp.isEmpty()) {
            Preference preference = getDefaultSettings(userId);
            preferenceRepository.save(preference);
            return preference;
        } else {
            return preferenceOp.get();
        }
    }

    /**
     * Update user preferences.
     *
     * @param userId The UUID of the user.
     * @param updatedPreference The updated preferences to save.
     */
    @Override
    public void update(UUID userId, Preference updatedPreference) {
        Preference preference = getByUserId(userId);

        preference.setDoNotDisturb(updatedPreference.isDoNotDisturb());
        preference.setMuteFrom(updatedPreference.getMuteFrom());
        preference.setMuteTo(updatedPreference.getMuteTo());
        preference.setGetPostInteractionNotification(updatedPreference.isGetPostInteractionNotification());
        preference.setGetConnectionInteractionNotification(updatedPreference.isGetConnectionInteractionNotification());
        preference.setGetHealthFeedNotification(updatedPreference.isGetHealthFeedNotification());
        preference.setGetRecommendationNotification(updatedPreference.isGetRecommendationNotification());
        preference.setGetAccountNotification(updatedPreference.isGetAccountNotification());

        preferenceRepository.save(preference);
    }

    /**
     * Reset user preferences to default settings.
     *
     * @param userId The UUID of the user.
     * @return The user's preferences after resetting to default.
     */
    @Override
    public Preference resetToDefault(UUID userId) {
        getByUserId(userId);
        update(userId, getDefaultSettings(userId));
        return getDefaultSettings(userId);
    }

    /**
     * Create and return default preferences for a user.
     *
     * @param userId The UUID of the user.
     * @return Default preferences.
     */
    private Preference getDefaultSettings(UUID userId) {
        // Create a new Preference object with default values
        Preference preference = new Preference();

        // Set the user ID to the provided userID
        preference.setUserId(userId);

        // Set default values for other fields
        preference.setDoNotDisturb(false);
        preference.setMuteFrom(null);
        preference.setMuteTo(null);
        preference.setGetPostInteractionNotification(true);
        preference.setGetConnectionInteractionNotification(true);
        preference.setGetHealthFeedNotification(true);
        preference.setGetRecommendationNotification(true);
        preference.setGetAccountNotification(true);

        return preference;
    }
}
