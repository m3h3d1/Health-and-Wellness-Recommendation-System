package com.healthapp.dataanalysisservice.controller;

import com.healthapp.dataanalysisservice.model.ConvertToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/data-collector")
public class DataCollectorController {

    @GetMapping("/test")
    public ResponseEntity<?> collectData(@RequestBody Object x) {

        String S = new ConvertToString().objectToString(x);
        return  ResponseEntity.ok(S);
}

}

