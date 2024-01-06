package com.example.demo.controllers;

import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path="/time", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeRestController {
    
    @GetMapping
    public ResponseEntity<String> getTimeAsJSON() {
        JsonObject response = Json.createObjectBuilder()
            .add("time", (new Date().toString()))
            .build();

        return ResponseEntity.ok(response.toString());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTimeAsJSON(@RequestBody String payload) {
        Reader reader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject data = jsonReader.readObject();
        System.out.println(">>>> " + data.toString());

        JsonObject response = Json.createObjectBuilder()
            .add("status", "modified")
            .add("adjustment", data.getInt("adjustment"))
            .build();

        return ResponseEntity
            .status(201)
            .header("my header", "fred")
            .body(response.toString());
    }
}
