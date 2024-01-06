package nus.iss.day15.redis.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nus.iss.day15.redis.cart.model.Item;

@Controller
@RequestMapping(path={"/", "/index.html"}) // try not to put verb in mapping
public class LandingPageController {
    @GetMapping
    public String getIndex(Model model) {
        // initialises item, add to model for view
        model.addAttribute("item", new Item());
        return "index";
    }
    
}
