package com.healthapp.communityservice.models.acheivementdto;

import com.healthapp.communityservice.entities.Achievement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class AchievementProgressReadDTO {
    private UUID achievementProgressId;
    private Double score;
    private Achievement achievement;
    private String completeness;
}
