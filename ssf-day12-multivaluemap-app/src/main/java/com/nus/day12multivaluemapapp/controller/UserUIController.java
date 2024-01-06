package com.nus.day12multivaluemapapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userview")
public class UserUIController {
    
    @GetMapping("/user")
    public String userForm() {
        return "createuser";
    }
}
