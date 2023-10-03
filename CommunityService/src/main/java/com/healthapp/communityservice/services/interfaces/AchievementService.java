package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.entities.Achievement;
import com.healthapp.communityservice.entities.AchievementStatistics;
import com.healthapp.communityservice.models.acheivementdto.AchievementDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementProgressCreateDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementStatisticsReadDTO;

import java.util.List;
import java.util.UUID;

public interface AchievementService {
    // Achievement CRUD operations
    public void create(AchievementDTO achievementDTO);
    public Achievement read(UUID achievementId);
    public List<Achievement> readAll();
    public void update(UUID achievementId, AchievementDTO achievementDTO);
    public void delete(UUID achievementId);

    // Achievement progress operations
    public void updateProgress(AchievementProgressCreateDTO achievementProgressDTO);
    public List<AchievementStatisticsReadDTO> getAchievementStatistics(UUID userId);
}
