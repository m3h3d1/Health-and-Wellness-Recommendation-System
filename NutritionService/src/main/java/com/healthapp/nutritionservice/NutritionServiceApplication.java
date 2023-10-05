package com.healthapp.nutritionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class NutritionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionServiceApplication.class, args);
	}

}
