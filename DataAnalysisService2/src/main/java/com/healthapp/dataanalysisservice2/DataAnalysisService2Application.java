package com.healthapp.dataanalysisservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DataAnalysisService2Application {

	public static void main(String[] args) {
		SpringApplication.run(DataAnalysisService2Application.class, args);
	}

}
