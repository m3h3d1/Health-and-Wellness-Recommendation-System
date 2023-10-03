package com.healthapp.mentalhealthservice.repository;

import com.healthapp.mentalhealthservice.entity.MoodLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoodLogRepository extends JpaRepository<MoodLog, UUID> {
}
