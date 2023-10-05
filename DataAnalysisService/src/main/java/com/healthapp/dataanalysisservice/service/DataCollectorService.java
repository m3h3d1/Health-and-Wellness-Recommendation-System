package com.healthapp.dataanalysisservice.service;

import com.healthapp.dataanalysisservice.network.MentalHealthExerciseServiceProxy;
import com.healthapp.dataanalysisservice.network.MoodTrackingServiceProxy;
import com.healthapp.dataanalysisservice.network.UserServiceProxy;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataCollectorService {
    private final UserServiceProxy userServiceProxy;
    private final MoodTrackingServiceProxy moodTrackingServiceProxy;
    private final MentalHealthExerciseServiceProxy mentalHealthExerciseServiceProxy;

    public DataCollectorService(UserServiceProxy userServiceProxy, MoodTrackingServiceProxy moodTrackingServiceProxy, MentalHealthExerciseServiceProxy mentalHealthExerciseServiceProxy) {
        this.userServiceProxy = userServiceProxy;
        this.moodTrackingServiceProxy = moodTrackingServiceProxy;
        this.mentalHealthExerciseServiceProxy = mentalHealthExerciseServiceProxy;
    }

    public Object CollectAllData(UUID userId) {

//        User Tracking Service Data

        Map<String, Object> unitedData = new HashMap<>();

        ResponseEntity<Object> userResponse;
        userResponse = userServiceProxy.getUser(userId);
        Object userData = userResponse.getBody();
        unitedData.put("userData", userData );



//        Mood Tracking Service Data

        ResponseEntity<Object> moodResponse;
        moodResponse = moodTrackingServiceProxy.getMoodTracking(userId);
        Object moodData = moodResponse.getBody();
        unitedData.put("moodData", moodData );




//        Mental Health Exerciser Tracking Service Data
        ResponseEntity<Object> exerciserResponse;
        exerciserResponse = mentalHealthExerciseServiceProxy.getMoodTracking(userId);
        Object exerciserData = exerciserResponse.getBody();
        unitedData.put("userData", userData );

        return  unitedData;
    }
}