package com.healthapp.dataanalysisservice.controller;

import com.healthapp.dataanalysisservice.service.DataCollectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/ml/data-collector")
public class DataCollectorController {
     private final DataCollectorService dataCollectorService;

    public DataCollectorController(DataCollectorService dataCollectorService) {
        this.dataCollectorService = dataCollectorService;
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> collectData(@PathVariable UUID userId) {
//        String stringData = dataCollectorService.CollectAllData(userId);
//        return ResponseEntity.ok(stringData);
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> collectData(@PathVariable UUID userId) {
        Object stringData = dataCollectorService.CollectAllData(userId);
        return ResponseEntity.ok(stringData);
    }
}

