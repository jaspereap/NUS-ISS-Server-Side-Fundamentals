package nus.iss.Day16Workshop.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Task {
    private String id;
    private Long createTime;
    private String name;
    private String desc;
    private String priority;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public static Task toTask(String payload) {
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject j = reader.readObject();

        Task task = new Task();
        task.setName(j.getString("name", ""));
        task.setDesc(j.getString("description", ""));
        task.setPriority(j.getString("priority", ""));

    }
}
