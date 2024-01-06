package com.example.demo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	// default port number
	private static final String DEFAULT_PORT = "3000";

	public static void main(String[] args) {

		// initialise Spring app
		SpringApplication app = new SpringApplication(DemoApplication.class);
		
		String port = null;
		// read args array and check for "Port" parameter
		// parses command line arguments
		DefaultApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		if (cliOpts.containsOption("port")) { // get port value if set in cli
			port = cliOpts.getOptionValues("port").get(0); // get first value
		}
		else if (System.getenv("PORT") != null) {
			port = System.getenv("PORT");
		} else {
			port = DEFAULT_PORT;
		}
		
		app.setDefaultProperties(
			Collections.singletonMap("server.port", port) // set port to listen before starting app
		);

		System.out.printf("Application started on port %s\n", port);

		// run spring boot app
		app.run(args);
	}



}
