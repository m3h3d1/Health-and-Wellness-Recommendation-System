package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.Height;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HeightRepository extends JpaRepository<Height, UUID> {
}
