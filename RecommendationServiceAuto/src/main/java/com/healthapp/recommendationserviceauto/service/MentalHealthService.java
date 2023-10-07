package com.healthapp.recommendationserviceauto.service;

import java.util.UUID;

public interface MentalHealthService {
    boolean ifExists(UUID recommendationId);
}
