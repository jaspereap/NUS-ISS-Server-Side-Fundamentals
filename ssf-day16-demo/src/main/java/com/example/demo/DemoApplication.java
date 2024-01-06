package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Get a jsonobjectbuilder and build
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		// Create json object
		JsonObject fred = builder
			.add("firstName", "fred")
			.add("lastName", "flintstone")
			.add("age", 50)
			.add("married", true)
			.add("height", 5.9)
			.build();

		JsonObject wilma = builder
			.add("firstName", "wilma")
			.add("lastName", "flintstone")
			.add("age", 30)
			.add("married", true)
			.add("height", 3.0)
			.add("spouse", fred)
			.build();

		JsonArray flintstones = Json.createArrayBuilder()
			.add(fred)
			.add(wilma)
			.build();

		System.out.printf(">>> fred:\n %s\n", fred.toString());
		System.out.println();
		System.out.printf(">>> wilma:\n %s\n", wilma.toString());
		System.out.println();
		System.out.printf(">>> Flintstones:\n %s\n", flintstones.toString());

		String name = wilma.getString("firstName");
		// String name = wilma.getString("firstName", "defaultValue");
		boolean married = wilma.getBoolean("married");
		Integer age = wilma.getInt("age");
		Double height = wilma.getJsonNumber("height").doubleValue();
		JsonObject spouse = wilma.getJsonObject("spouse");

		//
		for (int i = 0; i < flintstones.size(); i++) {
			JsonObject o = flintstones.getJsonObject(i);
			System.out.println(">>>>" + i + o);
		}

		// Stream
		flintstones.stream()
			.map(v -> v.asJsonObject())
			.forEach(jo -> {
				System.out.println("STREAM: " + jo);
			});
	}


}
