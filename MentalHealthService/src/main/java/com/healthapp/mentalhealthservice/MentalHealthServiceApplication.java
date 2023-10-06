package com.healthapp.mentalhealthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MentalHealthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalHealthServiceApplication.class, args);
	}

}
