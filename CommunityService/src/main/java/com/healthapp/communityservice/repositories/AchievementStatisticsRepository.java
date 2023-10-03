package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.AchievementStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementStatisticsRepository extends JpaRepository<AchievementStatistics, UUID> {
}
