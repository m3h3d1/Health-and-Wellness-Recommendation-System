package com.healthapp.dataanalysisservice2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GptResponseObject {
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("healthBrief")
    private String healthBrief;

    @JsonProperty("analyzedResults")
    private String analyzedResults;

    // Getter and setter methods for the fields

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHealthBrief() {
        return healthBrief;
    }

    public void setHealthBrief(String healthBrief) {
        this.healthBrief = healthBrief;
    }

    public String getAnalyzedResults() {
        return analyzedResults;
    }

    public void setAnalyzedResults(String analyzedResults) {
        this.analyzedResults = analyzedResults;
    }
}

