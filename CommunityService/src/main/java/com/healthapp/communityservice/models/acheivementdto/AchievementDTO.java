package com.healthapp.communityservice.models.acheivementdto;

import com.healthapp.communityservice.enums.AchievementDifficulty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AchievementDTO {
    private String title;
    private String goalDescription;
    private Double goalScore;
    private AchievementDifficulty difficulty;
}