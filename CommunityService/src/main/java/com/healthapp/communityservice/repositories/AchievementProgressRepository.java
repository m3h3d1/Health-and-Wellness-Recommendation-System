package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.AchievementProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementProgressRepository extends JpaRepository<AchievementProgress, UUID> {
}
