package com.healthapp.dataanalysisservice2.controller;

import com.healthapp.dataanalysisservice2.exception.DataNotFindByUserIdException;
import com.healthapp.dataanalysisservice2.service.DataCollectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ml/data-collector")
public class DataCollectorController {
    private final DataCollectorService dataCollectorService;

    public DataCollectorController(DataCollectorService dataCollectorService) {
        this.dataCollectorService = dataCollectorService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> collectData(@PathVariable UUID userId) {

        Object stringData = dataCollectorService.CollectAllData(userId);

        if (stringData!=null) {
            String successMessage = "Data analysis completed successfully";
            return ResponseEntity.ok(successMessage + ": " + stringData);
        } else {
            String errorMessage = "Data not found for the specified user ID";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}

