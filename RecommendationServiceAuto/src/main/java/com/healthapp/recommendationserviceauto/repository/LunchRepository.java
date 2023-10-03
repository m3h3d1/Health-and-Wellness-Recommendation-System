package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LunchRepository extends JpaRepository<Lunch, UUID> {
}
