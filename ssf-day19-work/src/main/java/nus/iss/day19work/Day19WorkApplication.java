package nus.iss.day19work;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@SpringBootApplication
public class Day19WorkApplication implements CommandLineRunner {

	@Autowired @Qualifier("redisTemplate")
	RedisTemplate<String, String> redisTemplate;


	public static void main(String[] args) {
		SpringApplication.run(Day19WorkApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		JsonReader jreader = Json.createReader(new BufferedReader(new FileReader("employee2.json")));
		JsonArray jarray = jreader.readArray();
		System.out.println(jarray);
		System.out.println(jarray.getJsonObject(0));
		System.out.println(jarray.getJsonObject(1));
		System.out.println(jarray.getJsonObject(2));
	}
}



