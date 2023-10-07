package com.healthapp.recommendationserviceauto.constants;

public class AppConstants {
    public static final String TOKEN_SECRET = "SM2P_GarbageCollectors";
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final String HEADER_STRING = "Authorization";
    public static final String ACTIVITY_DATA = "/health/add-activity";
    public static final String EXERCISE_DATA = "/health/exercise/add";
    public static final String EXERCISE_RECOMMENDATION = "/health/exercise/{recommendId}";
    public static final String SLEEP_RECOMMENDATION = "/health/sleep/{recommendId}";
    public static final String DIET_RECOMMENDATION = "/health/diet/{recommendId}";
    public static final String TOKEN_PREFIX = "Bearer ";
}

