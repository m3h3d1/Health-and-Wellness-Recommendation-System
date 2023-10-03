package com.healthapp.communityservice.models.acheivementdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class AchievementStatisticsReadDTO {
    private UUID achievementStatsId;
    private List<AchievementProgressReadDTO> progresses;
    private UUID userId;
}
