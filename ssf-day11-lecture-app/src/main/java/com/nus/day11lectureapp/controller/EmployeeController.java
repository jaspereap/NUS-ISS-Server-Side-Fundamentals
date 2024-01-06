package com.nus.day11lectureapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nus.day11lectureapp.model.Employee;

@RestController // special controller that returns json or xml
@RequestMapping("/employees") // maps to "/employees"
public class EmployeeController {

    @GetMapping(path = "/list") // maps to "/employees/list"
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("jasper", "eap", "jaeap@visa.com"));
        employees.add(new Employee("chonky", "eap", "cheap@visa.com"));
        return employees;
    }
}
