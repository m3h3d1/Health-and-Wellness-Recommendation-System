package com.healthapp.mentalhealthservice.service;

import com.healthapp.mentalhealthservice.dto.MoodLogDTO;
import com.healthapp.mentalhealthservice.entity.MoodLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface  MoodLogService {
     MoodLog createMoodLog(MoodLogDTO moodLogDTO);
     MoodLog updateMoodLog(UUID moodLogId, MoodLogDTO moodLogDTO);
     MoodLog getMoodLogById(UUID moodLogId);
     List<MoodLog> getMoodLogsByUserId(UUID userId);
     boolean deleteMoodLog(UUID moodLogId);
     List<MoodLog> getAllMoodLogs();
}
