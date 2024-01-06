package nus.iss.Day16Workshop.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.Day16Workshop.model.Task;

@Service
public class TaskService {
    
    // @Autowired
    // private TaskRepository taskRepo;

    public Task save(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setCreateTime((new Date()).getTime());

        // save to redis
        // taskRepo.save(task);

        return task;
    }

}
