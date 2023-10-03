package com.healthapp.communityservice.models.acheivementdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class AchievementProgressCreateDTO {
    private double score;
    private UUID achievementId;
    private UUID userId;
}
