package com.healthapp.dataanalysisservice.config;

import com.healthapp.dataanalysisservice.network.GPTServiceProxy;
import com.healthapp.dataanalysisservice.network.MentalHealthExerciseServiceProxy;
import com.healthapp.dataanalysisservice.network.MoodTrackingServiceProxy;
import com.healthapp.dataanalysisservice.network.UserServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
@Configuration
public class ProxyConfig {


    private final RestTemplate restTemplate;

    public ProxyConfig() {
        this.restTemplate = new RestTemplate(); // You should configure this RestTemplate as needed
    }

    @Bean
    public UserServiceProxy userServiceProxy() {
        return new UserServiceProxy() {
            @Override
            public ResponseEntity<Object> getUser(UUID userId) {
                String url = "http://localhost:9090/user-app/users/read-by-id/" + userId; // Replace with the actual URL
                ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
                return response;
            }
        };
    }


    @Bean
    public MoodTrackingServiceProxy moodTrackingServiceProxy() {
        return new MoodTrackingServiceProxy() {
            @Override
            public ResponseEntity<Object> getMoodTracking(UUID userId) {
                String url = "http://localhost:8700/mental-health/mood-tracking/user/" + userId; // Replace with the actual URL
                ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
                return response;
            }
        };
    }

    @Bean
    public MentalHealthExerciseServiceProxy mentalHealthExerciseServiceProxy() {
        return new MentalHealthExerciseServiceProxy() {
            @Override
            public ResponseEntity<Object> getMentalHealthExercise(UUID userId) {
                String url = "http://localhost:8700/mental-health/exercises/user/" + userId; // Replace with the actual URL
                ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
                return response;
            }
        };
    }

    @Bean
    public GPTServiceProxy gptServiceProxy() {
        return new GPTServiceProxy() {
            @Override
            public ResponseEntity<Object> getData(String prompt) {
                String url = "http://localhost:8010/bot/chat?prompt=" + prompt; // Replace with the actual URL
                ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
                return response;
            }
        };
    }

}