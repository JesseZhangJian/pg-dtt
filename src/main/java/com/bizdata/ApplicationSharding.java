package com.bizdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApplicationSharding {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationSharding.class, args);
	}
}
