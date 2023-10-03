package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.HealthNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HealthNotesRepository extends JpaRepository<HealthNotes, UUID> {
}
