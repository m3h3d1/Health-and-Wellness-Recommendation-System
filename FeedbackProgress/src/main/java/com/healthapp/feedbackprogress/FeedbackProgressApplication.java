package com.healthapp.feedbackprogress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeedbackProgressApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackProgressApplication.class, args);
	}

}
