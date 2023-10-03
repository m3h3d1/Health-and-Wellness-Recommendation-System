package com.healthapp.mentalhealthservice.service;

import com.healthapp.mentalhealthservice.dto.MoodLogDTO;
import com.healthapp.mentalhealthservice.entity.MoodLog;
import com.healthapp.mentalhealthservice.repository.MoodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MoodLogServiceImpl implements MoodLogService {

    private final MoodLogRepository moodLogRepository;

    @Autowired
    public MoodLogServiceImpl(MoodLogRepository moodLogRepository) {
        this.moodLogRepository = moodLogRepository;
    }

    @Override
    public MoodLog createMoodLog(MoodLogDTO moodLogDTO) {
        MoodLog moodLog = new MoodLog();
        // Set the 'date' field to the current time in Bangladesh
        ZoneId zoneId = ZoneId.of("Asia/Dhaka");
        LocalDateTime currentDateTime = LocalDateTime.now(zoneId);
        moodLog.setDate(currentDateTime);
        moodLog.setMoodRating(moodLogDTO.getMoodRating());
        moodLog.setNote(moodLogDTO.getNote());
        moodLog.setUserId(moodLogDTO.getUserId());
        return moodLogRepository.save(moodLog);
    }

    @Override
    public MoodLog updateMoodLog(UUID id, MoodLogDTO moodLogDTO) {
        Optional<MoodLog> moodLogOptional = moodLogRepository.findById(id);
        if (moodLogOptional.isPresent()) {
            MoodLog moodLog = moodLogOptional.get();
            // Update properties of moodLog based on moodLogDTO
            moodLog.setDate(moodLogDTO.getDate());
            moodLog.setMoodRating(moodLogDTO.getMoodRating());
            moodLog.setNote(moodLogDTO.getNote());
            moodLog.setUserId(moodLogDTO.getUserId());
            return moodLogRepository.save(moodLog);
        }
        return null; // or throw an exception if mood log with the given id is not found
    }

    @Override
    public MoodLog getMoodLogById(UUID id) {
        Optional<MoodLog> moodLogOptional = moodLogRepository.findById(id);
        return moodLogOptional.orElse(null);
    }


    @Override
    public boolean deleteMoodLog(UUID id) {
        Optional<MoodLog> moodLogOptional = moodLogRepository.findById(id);
        if (moodLogOptional.isPresent()) {
            moodLogRepository.deleteById(id);
            return true; // Deletion was successful
        } else {
            return false; // MoodLog with the specified ID was not found
        }
    }

    @Override
    public List<MoodLog> getAllMoodLogs() {
        return moodLogRepository.findAll();
    }
}

