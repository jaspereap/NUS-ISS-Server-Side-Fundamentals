package nus.iss.Day16Workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nus.iss.Day16Workshop.model.Task;
import nus.iss.Day16Workshop.service.TaskService;



@RestController
@RequestMapping(path="/api/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Autowired
    private TaskService taskSvc;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTask(@RequestBody String payload) {

        Task task = Task.toTask(payload);

        task = taskSvc.save(task);

        JsonObject response = Json.createObjectBuilder()
                .add("id", task.getId())
                .add("createTime", task.getCreateTime())
                .build();

        return ResponseEntity.ok(response.toString());
    }
}
