package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.entities.Achievement;
import com.healthapp.communityservice.models.acheivementdto.AchievementDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementProgressCreateDTO;
import com.healthapp.communityservice.models.acheivementdto.AchievementStatisticsReadDTO;
import com.healthapp.communityservice.services.interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/community/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    // Create a new achievement
    @PostMapping
    public ResponseEntity<String> createAchievement(@RequestBody AchievementDTO achievementDTO) {
        achievementService.create(achievementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Achievement created successfully");
    }

    // Get details of a specific achievement by ID
    @GetMapping("/{achievementId}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable UUID achievementId) {
        Achievement achievement = achievementService.read(achievementId);
        return ResponseEntity.ok(achievement);
    }

    // Get a list of all achievements
    @GetMapping
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        List<Achievement> achievements = achievementService.readAll();
        return ResponseEntity.ok(achievements);
    }

    // Update an existing achievement
    @PutMapping("/{achievementId}")
    public ResponseEntity<String> updateAchievement(
            @PathVariable UUID achievementId,
            @RequestBody AchievementDTO achievementDTO) {
        achievementService.update(achievementId, achievementDTO);
        return ResponseEntity.ok("Achievement updated successfully");
    }

    // Delete an achievement by ID
    @DeleteMapping("/{achievementId}")
    public ResponseEntity<String> deleteAchievement(@PathVariable UUID achievementId) {
        achievementService.delete(achievementId);
        return ResponseEntity.ok("Achievement deleted successfully");
    }

    // Update achievement progress
    @PostMapping("/progress")
    public ResponseEntity<String> updateAchievementProgress(@RequestBody AchievementProgressCreateDTO achievementProgressDTO) {
        achievementService.updateProgress(achievementProgressDTO);
        return ResponseEntity.ok("Achievement progress updated successfully");
    }

    // Get achievement statistics for a specific user
    @GetMapping("/statistics/{userId}")
    public ResponseEntity<List<AchievementStatisticsReadDTO>> getAchievementStatistics(@PathVariable UUID userId) {
        List<AchievementStatisticsReadDTO> statistics = achievementService.getAchievementStatistics(userId);
        return ResponseEntity.ok(statistics);
    }
}
