package com.healthapp.notificationservice.repository;

import com.healthapp.notificationservice.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
    Optional<Preference> findByUserId(UUID userId);
}
