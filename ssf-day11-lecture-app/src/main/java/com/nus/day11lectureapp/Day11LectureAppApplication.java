package com.nus.day11lectureapp;

import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class Day11LectureAppApplication {

	public static void main(String[] args) {
		// SpringApplication.run(Day11LectureAppApplication.class, args);

		SpringApplication app = new SpringApplication(Day11LectureAppApplication.class);
		// System.out.println("Starting app on port 8080");
		String port = "8080";
		ApplicationArguments argOptions = new DefaultApplicationArguments(args);
		// check for "port" argument passed
		if (argOptions.containsOption("port")) {
			// .getOptionValues() -> collection
			// .get(0) -> get first element of collection
			port = argOptions.getOptionValues("port").get(0);
		}
		// singletonMap() -> concise way to create immutable HashMap
		// no need to create a HashMap, then add to it.
		app.setDefaultProperties(Collections.singletonMap("server.port",port));
		app.run(args);
		System.out.println("Spring-boot app has started");
	}

	@Bean
	public CommonsRequestLoggingFilter log() {
		CommonsRequestLoggingFilter logger = new CommonsRequestLoggingFilter();
		logger.setIncludeClientInfo(true);
		logger.setIncludeQueryString(true);
		return logger;
	}

}
