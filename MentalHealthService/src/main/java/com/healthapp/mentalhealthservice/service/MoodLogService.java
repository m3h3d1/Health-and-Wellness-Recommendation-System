package com.healthapp.mentalhealthservice.service;

import com.healthapp.mentalhealthservice.dto.MoodLogDTO;
import com.healthapp.mentalhealthservice.entity.MoodLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface  MoodLogService {
     MoodLog createMoodLog(MoodLogDTO moodLogDTO);
     MoodLog updateMoodLog(UUID id, MoodLogDTO moodLogDTO);
     MoodLog getMoodLogById(UUID id);
     boolean deleteMoodLog(UUID id);
     List<MoodLog> getAllMoodLogs();
}
