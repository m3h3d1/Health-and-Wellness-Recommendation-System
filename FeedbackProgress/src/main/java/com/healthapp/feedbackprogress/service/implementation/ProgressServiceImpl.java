package com.healthapp.feedbackprogress.service.implementation;

import com.healthapp.feedbackprogress.dto.ProgressInsightDTO;
import com.healthapp.feedbackprogress.dto.healthdto.*;
import com.healthapp.feedbackprogress.network.HealthServiceProxy;
import com.healthapp.feedbackprogress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProgressServiceImpl implements ProgressService {
    @Autowired
    HealthServiceProxy healthServiceProxy;

    @Override
    public HealthDTO getProgressTrackById(UUID userId) {
        HealthDTO health = healthServiceProxy.getHealth(userId).getBody();

        health.getWeights().sort(Comparator.comparing(Weight::getDateTime));
        health.getHeights().sort(Comparator.comparing(Height::getDateTime));
        health.getDiabetes().sort(Comparator.comparing(Diabetes::getDateTime));
        health.getBloodPressures().sort(Comparator.comparing(BloodPressure::getDateTime));
        health.getHeartRates().sort(Comparator.comparing(HeartRate::getDateTime));
        health.getDiseases().sort(Comparator.comparing(Disease::getDate));

        return health;
    }

    @Override
    public ProgressInsightDTO getProgressInsightById(UUID userId) {
        HealthDTO health = healthServiceProxy.getHealth(userId).getBody();

        health.getWeights().sort(Comparator.comparing(Weight::getDateTime));
        health.getHeights().sort(Comparator.comparing(Height::getDateTime));
        health.getDiabetes().sort(Comparator.comparing(Diabetes::getDateTime));
        health.getBloodPressures().sort(Comparator.comparing(BloodPressure::getDateTime));
        health.getHeartRates().sort(Comparator.comparing(HeartRate::getDateTime));
        health.getDiseases().sort(Comparator.comparing(Disease::getDate));

        ProgressInsightDTO progressInsightDTO = new ProgressInsightDTO();

        progressInsightDTO.setHeightInsight(generateHeightInsights(health.getHeights()));
        progressInsightDTO.setWeightInsight(generateWeightInsights(health.getWeights(), health.getGoalWeight()));
        progressInsightDTO.setDiabetesInsight(generateDiabetesInsights(health.getDiabetes()));
        progressInsightDTO.setBloodPressureInsight(generateBloodPressureInsights(health.getBloodPressures()));
        progressInsightDTO.setHeartRateInsight(generateHeartRateInsights(health.getHeartRates()));
        progressInsightDTO.setDiseaseInsight(generateDiseasesInsights(health.getDiseases()));

        return progressInsightDTO;
    }

    public String generateHeightInsights(List<Height> heights) {

        if (heights.isEmpty()) {
            return "No height data available.";
        }

        if (heights.size() < 2) {
            return "Insufficient height data to generate insights. At least 2 height data needed.";
        }

        StringBuilder insights = new StringBuilder();
        insights.append("Height Insights:\n");

        double initialHeight = heights.get(0).getHeightInCm();
        double currentHeight = heights.get(heights.size() - 1).getHeightInCm();
        double heightChange = currentHeight - initialHeight;

        insights.append("Initial Height: ").append(initialHeight).append(" cm\n");
        insights.append("Current Height: ").append(currentHeight).append(" cm\n");

        if (heightChange > 0) {
            insights.append("You have grown by ").append(heightChange).append(" cm since you started tracking your height.\n");
        } else if (heightChange < 0) {
            insights.append("Your height has decreased by ").append(-heightChange).append(" cm since you started tracking your height.\n");
        } else {
            insights.append("Your height has remained the same since you started tracking.\n");
        }

        return insights.toString();
    }

    public String generateWeightInsights(List<Weight> weights, int goalWeight) {

        if (weights.isEmpty()) {
            return "No weight data available.";
        }

        StringBuilder insights = new StringBuilder();
        insights.append("Weight Insights:\n");

        double initialWeight = weights.get(0).getWeightInKg();
        double currentWeight = weights.get(weights.size() - 1).getWeightInKg();
        double weightChange = currentWeight - initialWeight;

        insights.append("Initial Weight: ").append(initialWeight).append(" kg\n");
        insights.append("Current Weight: ").append(currentWeight).append(" kg\n");
        insights.append("Weight Change: ").append(weightChange).append(" kg\n");

        if (weightChange > 0) {
            insights.append("You have gained ").append(weightChange).append(" kg since you started tracking your weight.\n");
        } else if (weightChange < 0) {
            insights.append("Congratulations! You have lost ").append(-weightChange).append(" kg since you started tracking your weight.\n");
        } else {
            insights.append("Your weight has remained the same since you started tracking.\n");
        }

        if (currentWeight < goalWeight) {
            insights.append("You are ").append(goalWeight - currentWeight).append(" kg away from your goal weight.\n");
        } else if (currentWeight > goalWeight) {
            insights.append("You have exceeded your goal weight by ").append(currentWeight - goalWeight).append(" kg.\n");
        } else {
            insights.append("Congratulations! You have reached your goal weight.\n");
        }

        return insights.toString();
    }

    public String generateDiabetesInsights(List<Diabetes> diabetesData) {

        if (diabetesData.isEmpty()) {
            return "No diabetes data available.";
        }

        Diabetes latestReading = diabetesData.get(diabetesData.size() - 1);
        double sugarLevel = latestReading.getSugarLevel();

        StringBuilder insight = new StringBuilder();
        insight.append("Current blood sugar level: ").append(sugarLevel).append(" mg/dL\n");

        if (sugarLevel <= 70) {
            insight.append("Your blood sugar level is low. Please consider having a snack or glucose to raise your sugar level.");
        } else if (sugarLevel > 70 && sugarLevel <= 180) {
            insight.append("Your blood sugar level is within the target range. Keep monitoring it regularly and maintain a balanced diet.");
        } else {
            insight.append("Your blood sugar level is high. It's essential to consult your healthcare provider for advice and consider adjustments to your diet and medication.");
        }

        return insight.toString();
    }

    public String generateBloodPressureInsights(List<BloodPressure> bloodPressureData) {

        if (bloodPressureData.isEmpty()) {
            return "No blood pressure data available.";
        }

        BloodPressure latestReading = bloodPressureData.get(bloodPressureData.size() - 1);
        double pressure = latestReading.getPressure();

        StringBuilder insight = new StringBuilder();
        insight.append("Current blood pressure: ").append(pressure).append(" mmHg\n");

        if (pressure < 90) {
            insight.append("Your blood pressure is low. Please consider consulting a healthcare professional.");
        } else if (pressure >= 90 && pressure <= 120) {
            insight.append("Your blood pressure is within the normal range. Maintain a healthy lifestyle to keep it in check.");
        } else if (pressure > 120 && pressure <= 140) {
            insight.append("Your blood pressure is slightly elevated. Focus on a balanced diet and regular exercise.");
        } else {
            insight.append("Your blood pressure is high. It's essential to consult your healthcare provider for guidance and consider medication if recommended.");
        }

        return insight.toString();
    }

    public String generateHeartRateInsights(List<HeartRate> heartRateData) {

        if (heartRateData.isEmpty()) {
            return "No heart rate data available.";
        }

        HeartRate latestReading = heartRateData.get(heartRateData.size() - 1);
        String beatsPerMinute = latestReading.getBeatsPerMin();

        StringBuilder insight = new StringBuilder();
        insight.append("Current heart rate: ").append(beatsPerMinute).append(" bpm\n");

        int heartRateValue = Integer.parseInt(beatsPerMinute);

        if (heartRateValue < 60) {
            insight.append("Your heart rate is below the normal range. It may indicate bradycardia. Please consult a healthcare professional.");
        } else if (heartRateValue <= 100) {
            insight.append("Your heart rate is within the normal range. Keep up a healthy lifestyle to maintain it.");
        } else {
            insight.append("Your heart rate is above the normal range. It may indicate tachycardia. Please consult your healthcare provider for further evaluation.");
        }

        return insight.toString();
    }

    public String generateDiseasesInsights(List<Disease> diseases) {

        if (diseases.isEmpty()) {
            return "No disease data available.";
        }

        StringBuilder insights = new StringBuilder();
        insights.append("Disease Insights:\n");

        for (Disease disease : diseases) {
            String diseaseName = disease.getDiseaseName();
            LocalDate date = disease.getDate();
            String note = disease.getNote();

            insights.append("Date: ").append(date).append("\n");
            insights.append("Disease: ").append(diseaseName).append("\n");
            insights.append("Note: ").append(note).append("\n\n");
        }

        return insights.toString();
    }
}
