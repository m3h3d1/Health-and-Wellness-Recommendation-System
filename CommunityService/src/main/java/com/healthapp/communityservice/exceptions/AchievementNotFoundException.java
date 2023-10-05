package com.healthapp.communityservice.exceptions;

public class AchievementNotFoundException extends NotFoundException{
    public AchievementNotFoundException(String message) {
        super("AchievementNotFoundException", "Fetching an achievement", message);
    }
}
