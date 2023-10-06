package com.healthapp.dataanalysisservice.model;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertToString {
    public String objectToString(Object objectToConvert) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objectToConvert);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


