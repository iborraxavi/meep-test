package com.xiborra.meep_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeepTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeepTestApplication.class, args);
	}

}
