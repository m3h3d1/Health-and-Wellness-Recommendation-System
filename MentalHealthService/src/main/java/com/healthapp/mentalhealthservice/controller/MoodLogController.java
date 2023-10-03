package com.healthapp.mentalhealthservice.controller;

import com.healthapp.mentalhealthservice.dto.MoodLogDTO;
import com.healthapp.mentalhealthservice.entity.MoodLog;
import com.healthapp.mentalhealthservice.service.MoodLogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mental-health/mood-tracking")
public class MoodLogController {

//    @Autowired
    private final MoodLogServiceImpl moodLogServiceImpl;

    public MoodLogController(MoodLogServiceImpl moodLogServiceImpl) {
        this.moodLogServiceImpl = moodLogServiceImpl;
    }

//    @PostMapping("/create")
//    public ResponseEntity<MoodLog> createMoodLog(@RequestBody MoodLogDTO moodLogDTO) {
//        MoodLog createdMoodLog = moodLogServiceImpl.createMoodLog(moodLogDTO);
//        return new ResponseEntity<>(createdMoodLog, HttpStatus.CREATED);
////        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
//    }

    @PostMapping("/create")
    public ResponseEntity<String> createMoodLog(@RequestBody MoodLogDTO moodLogDTO) {
        MoodLog createdMoodLog = moodLogServiceImpl.createMoodLog(moodLogDTO);
        if (createdMoodLog != null) {
            return new ResponseEntity<>("MoodLog created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create MoodLog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MoodLog>> getAllMoodLogs() {
        List<MoodLog> moodLogs = moodLogServiceImpl.getAllMoodLogs();
        return new ResponseEntity<>(moodLogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoodLog> getMoodLogById(@PathVariable UUID id) {
        MoodLog moodLog = moodLogServiceImpl.getMoodLogById(id);
        if (moodLog != null) {
            return new ResponseEntity<>(moodLog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoodLog> updateMoodLog(@PathVariable UUID id, @RequestBody MoodLogDTO moodLogDTO) {
        MoodLog updatedMoodLog = moodLogServiceImpl.updateMoodLog(id, moodLogDTO);
        if (updatedMoodLog != null) {
            return new ResponseEntity<>(updatedMoodLog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoodLog(@PathVariable UUID id) {
        boolean deleted = moodLogServiceImpl.deleteMoodLog(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

