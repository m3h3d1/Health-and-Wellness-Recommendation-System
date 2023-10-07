package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Achievement;
import com.healthapp.communityservice.entities.AchievementProgress;
import com.healthapp.communityservice.entities.AchievementStatistics;
import com.healthapp.communityservice.exceptions.AchievementNotFoundException;
import com.healthapp.communityservice.models.acheivementdto.AchievementDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementProgressCreateDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementProgressReadDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementStatisticsReadDTO;
import com.healthapp.communityservice.networks.NotificationDTO;
import com.healthapp.communityservice.networks.NotificationServiceProxy;
import com.healthapp.communityservice.repositories.AchievementRepository;
import com.healthapp.communityservice.repositories.AchievementStatisticsRepository;
import com.healthapp.communityservice.services.interfaces.AchievementService;
import com.healthapp.communityservice.utilities.constants.TokenConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementStatisticsRepository achievementStatisticsRepository;
    private final NotificationServiceProxy notificationServiceProxy;

    public AchievementServiceImpl(AchievementRepository achievementRepository, AchievementStatisticsRepository achievementStatisticsRepository, NotificationServiceProxy notificationServiceProxy) {
        this.achievementRepository = achievementRepository;
        this.achievementStatisticsRepository = achievementStatisticsRepository;
        this.notificationServiceProxy = notificationServiceProxy;
    }

    /**
     * Create a new achievement.
     *
     * @param achievementDTO The DTO containing achievement creation data.
     */
    @Override
    public void create(AchievementDTO achievementDTO) {
        Achievement achievement = new Achievement();
        achievement.setDifficulty(achievementDTO.getDifficulty());
        achievement.setTitle(achievementDTO.getTitle());
        achievement.setGoalDescription(achievementDTO.getGoalDescription());
        achievement.setGoalScore(achievementDTO.getGoalScore());
        achievementRepository.save(achievement);
    }

    /**
     * Read an achievement by its ID.
     *
     * @param achievementId The UUID of the achievement to read.
     * @return The Achievement object representing the achievement.
     * @throws AchievementNotFoundException If the achievement does not exist.
     */
    @Override
    public Achievement read(UUID achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        if (achievement.isEmpty()) {
            throw new AchievementNotFoundException("Achievement with given ID does not exist.");
        }
        return achievement.get();
    }

    /**
     * Get a list of all achievements.
     *
     * @return A list of Achievement objects representing all achievements.
     */
    @Override
    public List<Achievement> readAll() {
        return achievementRepository.findAll();
    }

    /**
     * Update an achievement.
     *
     * @param achievementId    The UUID of the achievement to update.
     * @param achievementDTO   The DTO containing updated achievement data.
     * @throws AchievementNotFoundException If the achievement does not exist.
     */
    @Override
    public void update(UUID achievementId, AchievementDTO achievementDTO) {
        Achievement achievement = read(achievementId);
        achievement.setGoalScore(achievementDTO.getGoalScore());
        achievement.setTitle(achievementDTO.getTitle());
        achievement.setGoalDescription(achievementDTO.getGoalDescription());
        achievement.setDifficulty(achievementDTO.getDifficulty());
        achievementRepository.save(achievement);
    }

    /**
     * Delete an achievement by its ID.
     *
     * @param achievementId The UUID of the achievement to delete.
     * @throws AchievementNotFoundException If the achievement does not exist.
     */
    @Override
    public void delete(UUID achievementId) {
        read(achievementId);
        achievementRepository.deleteById(achievementId);
    }

    /**
     * Update achievement progress for a user.
     *
     * @param achievementProgressDTO The DTO containing the achievement progress data.
     */
    @Override
    public void updateProgress(AchievementProgressCreateDTO achievementProgressDTO) {
        AchievementStatistics statistics = new AchievementStatistics();
        statistics.setUserId(achievementProgressDTO.getUserId());
        List<AchievementStatistics> statisticsList = achievementStatisticsRepository
                .findAll().stream()
                .filter(stats -> stats.getUserId().equals(achievementProgressDTO.getUserId()))
                .toList();
        if (!statisticsList.isEmpty()) {
            statistics = statisticsList.get(0);
        }

        if (statistics.getProgresses() == null) {
            statistics.setProgresses(new ArrayList<>());
        }
        for (AchievementProgress existingProgress : statistics.getProgresses()) {
            if (existingProgress.getAchievement().getAchievementId().equals(achievementProgressDTO.getAchievementId())) {
                double previousScore = existingProgress.getScore();
                existingProgress.setScore(existingProgress.getScore() + achievementProgressDTO.getScore());
                double currentScore = existingProgress.getScore();
                if(previousScore < existingProgress.getAchievement().getGoalScore() && currentScore >= existingProgress.getAchievement().getGoalScore()){
                    // Send notification
                    NotificationDTO notification = new NotificationDTO();
                    // Send a notification to the user congratulating for the new achievement
                    notification.setText("Congratulations, you just achieved a new milestone \"" +
                            existingProgress.getAchievement().getTitle()
                            + "\"! Keep up the incredible effort!");
                    notificationServiceProxy.send(achievementProgressDTO.getUserId(), TokenConstants.TOKEN_SECRET, notification);
                }
                achievementStatisticsRepository.save(statistics);
                return;
            }
        }

        AchievementProgress progress = new AchievementProgress();
        Achievement achievement = read(achievementProgressDTO.getAchievementId());
        progress.setAchievement(achievement);
        progress.setScore(achievementProgressDTO.getScore());
        progress.setStatistics(statistics);
        statistics.getProgresses().add(progress);
        achievementStatisticsRepository.save(statistics);
    }

    /**
     * Get achievement statistics for a user.
     *
     * @param userId The UUID of the user.
     * @return A list of AchievementStatisticsReadDTO objects representing the user's achievement statistics.
     */
    @Override
    public List<AchievementStatisticsReadDTO> getAchievementStatistics(UUID userId) {
        return achievementStatisticsRepository
                .findAll().stream()
                .filter(stats -> stats.getUserId().equals(userId))
                .map(stats -> {
                    AchievementStatisticsReadDTO statsRead = new AchievementStatisticsReadDTO();
                    statsRead.setUserId(stats.getUserId());
                    statsRead.setAchievementStatsId(stats.getAchievementStatsId());
                    statsRead.setProgresses(stats.getProgresses()
                            .stream().map(progress -> {
                                AchievementProgressReadDTO progressRead = new AchievementProgressReadDTO();
                                progressRead.setAchievementProgressId(progress.getAchievementProgressId());
                                progressRead.setAchievement(progress.getAchievement());
                                progressRead.setScore(progress.getScore());
                                Double target = progress.getAchievement().getGoalScore();
                                Double completed = progress.getScore();
                                double completeness = (target == 0 ? 0 : completed / target) * 100;
                                completeness = completeness > 100 ? 100 : completeness;
                                progressRead.setCompleteness(String.format("%.2f", completeness) + "%");
                                return progressRead;
                            }).collect(Collectors.toList())
                    );
                    return statsRead;
                })
                .collect(Collectors.toList());
    }
}
