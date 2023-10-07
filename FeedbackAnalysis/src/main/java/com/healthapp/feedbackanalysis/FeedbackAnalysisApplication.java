package com.healthapp.feedbackanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeedbackAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackAnalysisApplication.class, args);
	}

}
