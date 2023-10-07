package com.healthapp.recommendationserviceauto.service.implementation;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.exception.EmptyResultException;
import com.healthapp.recommendationserviceauto.model.recommendationdto.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.entrydatadto.ExerciseRequestDto;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.BloodPressureRepository;
import com.healthapp.recommendationserviceauto.repository.ExerciseRecommendationRepository;
import com.healthapp.recommendationserviceauto.repository.ExerciseRepository;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.service.interfaces.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private BloodPressureRepository bloodPressureRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseRecommendationRepository exerciseRecommendationRepository;
    private Integer goalWeight;
    private Integer targetPeriod;
    private Integer age;
    @Override
    public ExerciseRecommendationDto recommend(UUID userId) {
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);

        if (!healthOptional.isPresent()) {
            throw new EmptyResultException();
        }

        Health health = healthOptional.get();
        List<BloodPressure> bloodPressures = health.getBloodPressures();
        List<Weight> weights = health.getWeights();
        List<Diabetes> diabetes = health.getDiabetes();
        List<Disease> diseases = health.getDiseases();

        // Sort health data by DateTime
        Collections.sort(bloodPressures, Comparator.comparing(BloodPressure::getDateTime).reversed());
        Collections.sort(weights, Comparator.comparing(Weight::getDateTime).reversed());
        Collections.sort(diabetes, Comparator.comparing(Diabetes::getDateTime).reversed());

        Double latestHighBloodPressure = bloodPressures.isEmpty() ? null : bloodPressures.get(0).getHighPressure();
        Double latestLowBloodPressure = bloodPressures.isEmpty() ? null : bloodPressures.get(0).getLowPressure();
        Double latestWeight = weights.isEmpty() ? null : weights.get(0).getWeightInKg();
        Double latestSugarLevel = diabetes.isEmpty() ? null : diabetes.get(0).getSugarLevel();

        age = health.getAge();
        goalWeight = health.getGoalWeight();
        targetPeriod = health.getTargetPeriod();

        String insights = generateInsights(latestHighBloodPressure, latestLowBloodPressure, latestWeight, latestSugarLevel, age, goalWeight, targetPeriod);
        String recommendationText = generateRecommendationText(latestHighBloodPressure, latestLowBloodPressure, latestWeight, latestSugarLevel, insights);

        String exerciseIntensity = determineExerciseIntensity(latestHighBloodPressure, latestLowBloodPressure, latestWeight, latestSugarLevel);

        List<Exercise> exercises =  exerciseRepository.findByIntensity(exerciseIntensity);

        ExerciseRecommendation exerciseRecommendation = createExerciseRecommendation(userId, latestSugarLevel, latestHighBloodPressure, latestLowBloodPressure, age, latestWeight, goalWeight, targetPeriod, diseases, insights, recommendationText, exercises);

        ExerciseRecommendationDto recommendationDto = createExerciseRecommendationDto(exerciseRecommendation);

        return recommendationDto;
    }
    private String generateInsights(Double latestHighBloodPressure, Double latestLowBloodPressure, Double latestWeight, Double latestSugarLevel, Integer age, Integer goalWeight, Integer targetPeriod) {
        StringBuilder insights = new StringBuilder();

        // Check blood pressure
        if (latestHighBloodPressure != null) {
            if (latestHighBloodPressure > 160.0) {
                insights.append("Your blood pressure is very high. ");
            } else if (latestHighBloodPressure > 140.0) {
                insights.append("Your blood pressure is high. ");
            }
        }
        if (latestLowBloodPressure != null) {
            if (latestLowBloodPressure < 70.0) {
                insights.append("Your blood pressure is very low. ");
            } else if (latestLowBloodPressure < 90.0) {
                insights.append("Your blood pressure is low. ");
            }
        }

        // Check weight and weight goal
        if (latestWeight != null && goalWeight != null) {
            if (latestWeight > goalWeight) {
                insights.append("You have exceeded your goal weight. ");
            } else if (latestWeight < goalWeight) {
                insights.append("You are below your goal weight. ");
            }
        }

        // Check sugar level (assuming sugar level is measured in mg/dL)
        if (latestSugarLevel != null) {
            if (latestSugarLevel > 180.0) {
                insights.append("Your blood sugar level is high. ");
            } else if (latestSugarLevel < 70.0) {
                insights.append("Your blood sugar level is low. ");
            }
        }

        // Check age and target period
        if (age != null && targetPeriod != null) {
            if (age > 40 && targetPeriod < 6) {
                insights.append("Given your age and short target period, it's important to focus on sustainable fitness goals. ");
            }
        }

        // Check overall health
        if (insights.length() == 0) {
            insights.append("You are maintaining good health. Keep up the good work!");
        } else {
            insights.append("Consider consulting a healthcare provider for personalized advice.");
        }

        return insights.toString();
    }

    private String generateRecommendationText(Double latestHighBloodPressure, Double latestLowBloodPressure, Double latestWeight, Double latestSugarLevel, String insights) {
        StringBuilder recommendationText = new StringBuilder();

        if (!insights.isEmpty()) {
            if (latestHighBloodPressure != null) {
                if (latestHighBloodPressure > 140.0) {
                    recommendationText.append("Consider avoiding strenuous exercises and consult a healthcare provider for your high blood pressure. ");
                } else if (latestLowBloodPressure != null && latestLowBloodPressure < 90.0) {
                    recommendationText.append("Consider light-intensity exercises and consult a healthcare provider for your low blood pressure. ");
                }
            }

            if (latestWeight != null) {
                if (latestWeight > goalWeight) {
                    recommendationText.append("Consider focusing on weight maintenance exercises to achieve your goal weight. ");
                } else if (latestWeight < goalWeight) {
                    recommendationText.append("Consider adding strength and resistance training to reach your goal weight. ");
                }
            }

            if (latestSugarLevel != null) {
                if (latestSugarLevel > 180.0) {
                    recommendationText.append("Consider exercises that help manage blood sugar levels and consult a healthcare provider for your high sugar levels. ");
                } else if (latestSugarLevel < 70.0) {
                    recommendationText.append("Consider including exercises that can help stabilize blood sugar levels. ");
                }
            }

            if (age != null && targetPeriod != null) {
                if (age > 40 && targetPeriod < 6) {
                    recommendationText.append("You might fail to achieve your goal. ");
                }
            }
        } else {
            recommendationText.append("You are on your way to achieve your goal");
        }

        return recommendationText.toString();
    }

    private String determineExerciseIntensity(Double latestHighBloodPressure, Double latestLowBloodPressure, Double latestWeight, Double latestSugarLevel) {
        String exerciseIntensity = "Medium";

        if (latestHighBloodPressure != null && latestHighBloodPressure > 140.0) {
            exerciseIntensity = "High";
        } else if (latestLowBloodPressure != null && latestLowBloodPressure < 90.0) {
            exerciseIntensity = "Low";
        } else if (latestWeight != null && latestWeight != null && latestWeight > goalWeight) {
            exerciseIntensity = "High";
        } else if (latestWeight != null && latestWeight < goalWeight) {
            exerciseIntensity = "Low";
        } else if (latestSugarLevel != null && latestSugarLevel > 180.0) {
            exerciseIntensity = "High";
        } else if (latestSugarLevel != null && latestSugarLevel < 70.0) {
            exerciseIntensity = "Low";
        }

        return exerciseIntensity;
    }

    private ExerciseRecommendation createExerciseRecommendation(UUID userId, Double latestSugarLevel, Double latestHighBloodPressure, Double latestLowBloodPressure, Integer age, Double latestWeight, Integer goalWeight, Integer targetPeriod, List<Disease> diseases, String insights, String recommendationText, List<Exercise> exercises) {
        ExerciseRecommendation exerciseRecommendation = new ExerciseRecommendation();
        exerciseRecommendation.setDateTime(LocalDateTime.now());
        exerciseRecommendation.setUserId(userId);
        exerciseRecommendation.setSugarLevel(latestSugarLevel);
        exerciseRecommendation.setHighBloodPressure(latestHighBloodPressure);
        exerciseRecommendation.setLowBloodPressure(latestLowBloodPressure);
        exerciseRecommendation.setAge(age);
        exerciseRecommendation.setWeight(latestWeight);
        exerciseRecommendation.setGoalWeight(goalWeight);
        exerciseRecommendation.setTargetPeriod(targetPeriod);
        exerciseRecommendation.copyDiseaseData(diseases);
        exerciseRecommendation.setMessage(recommendationText);
        exerciseRecommendation.setInsights(insights);
        exerciseRecommendation.setExerciseList(exercises);

        exerciseRecommendationRepository.save(exerciseRecommendation);
        return exerciseRecommendation;
    }

    private ExerciseRecommendationDto createExerciseRecommendationDto(ExerciseRecommendation exerciseRecommendation) {
        ExerciseRecommendationDto recommendationDto = new ExerciseRecommendationDto();
        recommendationDto.setInsights(exerciseRecommendation.getInsights());
        recommendationDto.setMessage(exerciseRecommendation.getMessage());
        recommendationDto.setExerciseList(exerciseRecommendation.getExerciseList());
        recommendationDto.setDate(exerciseRecommendation.getDateTime());
        return recommendationDto;
    }



    @Override
    public void addExerciseData(ExerciseRequestDto exerciseRequestDto) {
        Exercise exercise =new Exercise();
        exercise.setDescription(exerciseRequestDto.getDescription());
        exercise.setCategory(exerciseRequestDto.getCategory());
        exercise.setIntensity(exerciseRequestDto.getIntensity());
        exercise.setDuration(exerciseRequestDto.getDuration());
        exercise.setCalorieBurn(exerciseRequestDto.getCalorieBurn());
        exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> getAllExercise() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<ExerciseRecommendation> getAllExerciseRecommendations() {
        return exerciseRecommendationRepository.findAll();
    }

    @Override
    public ExerciseRecommendation getRecommendationById(UUID recommendationId) {
        return exerciseRecommendationRepository.findById(recommendationId).get();
    }
    @Override
    public boolean ifExists(UUID recommendationId){
        return exerciseRecommendationRepository.existsById(recommendationId);
    }
}
