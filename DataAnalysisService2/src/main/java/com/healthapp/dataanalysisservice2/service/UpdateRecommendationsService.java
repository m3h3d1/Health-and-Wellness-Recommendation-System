package com.healthapp.dataanalysisservice2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthapp.dataanalysisservice2.model.GptResponseObject;
import com.healthapp.dataanalysisservice2.network.GPTServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateRecommendationsService {

    private final String prefix = "The following text is a string of json data of a particular user,\n" +
                "now observe the data carefully, what is there. Then analyse those and\n" +
                "provide your insights. Do not write/reply with any additional message such as\n" +
                "'I understood your request and here is the analyse data.'  Just write like this:\n";


    private final DataCollectorService dataCollectorService;
    private final GPTServiceProxy gptServiceProxy;

    @Autowired
    public UpdateRecommendationsService(DataCollectorService dataCollectorService, GPTServiceProxy gptServiceProxy) {
        this.dataCollectorService = dataCollectorService;
        this.gptServiceProxy = gptServiceProxy;
    }

    public String UpdateRecommendationsData(UUID userId) throws JsonProcessingException {

            // Step 1: Collect JSON data using DataCollectorService
            Object jsonData = dataCollectorService.CollectAllData(userId);

            // Serialize jsonData to JSON string
            String jsonDataString = new ObjectMapper().writeValueAsString(jsonData);
            if(jsonDataString.length() < 40) return null;

            // Step 2: Call the GPT service to analyze the data
            String gptResponse = gptServiceProxy.getData(prefix+" Here is data: "+jsonDataString);

            return gptResponse;
    }

    private String extractUpdateRecommendationsData(ResponseEntity<Object> gptResponse) {
        try {
            // Check if the response status is successful (e.g., HTTP 200)
            if (gptResponse.getStatusCode().is2xxSuccessful()) {
                // Extract the response body as a JSON string
                String responseBody = new ObjectMapper().writeValueAsString(gptResponse.getBody());

                // Parse the JSON response to a Java object
                GptResponseObject gptResponseObject = new ObjectMapper().readValue(responseBody, GptResponseObject.class);

                // Extract and return the relevant data fields from the parsed object
                String userName = gptResponseObject.getUserName();
                String healthBrief = gptResponseObject.getHealthBrief();
                String analyzedResults = gptResponseObject.getAnalyzedResults();

                return "User Name: " + userName + ",\n" +
                        "Health brief: " + healthBrief + ",\n" +
                        "Analyzed results: " + analyzedResults;
            } else {
                // Handle the case when the GPT service response is not successful
                return "Failed to analyze data";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            return "Error occurred during analysis";
        }
    }
}

