package com.nus.day12randomnumberapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.nus.day12randomnumberapp.model.Generate;

@Controller
@RequestMapping("/random")
public class RandGeneratorController {
    // Show form for generating
    // Inject Model to bind data to view
    @GetMapping("/show")
    public String showRandomForm(Model model) {
        // initialises a new Generate form when index is visited
        Generate g = new Generate();
        // binds variable g to Model
        // access via ${generateObj} syntax in TL / HTML
        model.addAttribute("generateObject", g);
        return "generate";
    }

    // Generate random numbers
    // Bind ?val=(value) query string input to this method's argument
    @GetMapping("/generate")
    public String generate(@RequestParam Integer val, Model model) {
        // System.out.println(val);
        this.randomizeNumber(model, val);
        return "result";
    }

    // @GetMapping("{city}/{lol}")
    // public String generate2(
    //     @PathVariable("city") String val,
    //     @PathVariable("lol") String haha) {
    //     System.out.println(val);
    //     System.out.println("Printing lol: " + haha);
    //     return "result";
    // }


    //
    private void randomizeNumber(Model model, int reps) {
        int max = 31;
        String[] imgNum = new String[max];

        Random rand = new Random();
        List<Integer> results = new ArrayList<>();
        while(results.size() < reps) {
            results.add(rand.nextInt(max));
        }

        // Generate String links to images
        List<String> selectedImages = new ArrayList<>();
        for (Integer number : results) {
            selectedImages.add("number"+number+".jpg");
        }
        // System.out.println(selectedImages);

        // 
        model.addAttribute("reps", reps);
        model.addAttribute("selectedImages", selectedImages);
        model.addAttribute("results", results);
    }
}
