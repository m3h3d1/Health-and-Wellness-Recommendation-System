package com.healthapp.dataanalysisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class DataAnalysisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAnalysisServiceApplication.class, args);
	}

}
