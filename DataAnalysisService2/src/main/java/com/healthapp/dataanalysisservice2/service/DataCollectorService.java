package com.healthapp.dataanalysisservice2.service;

import com.healthapp.dataanalysisservice2.network.InternalCommunicationException;
import com.healthapp.dataanalysisservice2.network.MentalHealthExerciseServiceProxy;
import com.healthapp.dataanalysisservice2.network.MoodTrackingServiceProxy;
import com.healthapp.dataanalysisservice2.network.UserServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
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
        try{
            userResponse = userServiceProxy.getUser(userId);

            Object userData = userResponse.getBody();
            unitedData.put("userData", userData );
        }
        catch (Exception ex){

        }

//        Mood Tracking Service Data

        ResponseEntity<Object> moodResponse;
        try{
            moodResponse = moodTrackingServiceProxy.getMoodTracking(userId);
            Object moodData = moodResponse.getBody();
            unitedData.put("moodData", moodData );
        }catch (Exception ex){

        }

//        Mental Health Exerciser Tracking Service Data
        ResponseEntity<Object> exerciserResponse;
        try{
            exerciserResponse = mentalHealthExerciseServiceProxy.getMentalHealthExercise(userId);
            Object exerciserData = exerciserResponse.getBody();
            unitedData.put("exerciserData", exerciserData );
        }catch (Exception ex){

        }

        return  unitedData;

    }
}
