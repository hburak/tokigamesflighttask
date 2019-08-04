package com.hburak.flight_checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FlightCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightCheckerApplication.class, args);
	}

}
