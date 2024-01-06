package nus.iss.day15.redis.cart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class LandingPageController {
    public String index() {
        return "index";
    }
}
