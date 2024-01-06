package com.nus.day11lectureapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Generate a constructor that takes all 3 fields as input
@NoArgsConstructor // Generates a constructor that takes no input
public class Employee {
    private String firstName;

    private String lastName;

    private String email;
}
