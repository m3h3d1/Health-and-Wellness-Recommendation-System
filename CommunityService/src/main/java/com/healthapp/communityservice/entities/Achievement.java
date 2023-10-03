package com.healthapp.communityservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.healthapp.communityservice.enums.AchievementDifficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "achievement_id")
    private UUID achievementId;

    private String title;
    private String goalDescription;
    private Double goalScore;
    private AchievementDifficulty difficulty;
}
