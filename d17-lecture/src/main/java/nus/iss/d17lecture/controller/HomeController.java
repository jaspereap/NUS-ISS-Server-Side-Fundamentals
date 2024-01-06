package nus.iss.d17lecture.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import nus.iss.d17lecture.model.Country;
import nus.iss.d17lecture.service.ProcessService;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    ProcessService processSvc;

    @GetMapping(path = "/booksearch")
    public String bookSearchForm() {

        return "booksearch";
    }
    @GetMapping(path = "/countries")
    public ResponseEntity<String> listCountries() {
        ResponseEntity<String> result = processSvc.getCountries();
        return result;
    }

    @GetMapping(path = "/countries/jsontest")
    public String listCountries2(Model model) {
        ResponseEntity<String> result = processSvc.getCountries();

        // Convert String into a Json object
        String jsonString = result.getBody().toString();
        
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        JsonObject jsonObjectData = jsonObject.getJsonObject("data");
        System.out.println("jsonObjectData: " + jsonObject);
        System.out.println("jsonObjectData size: " + jsonObjectData.size());

        List<Country> countries = new ArrayList<>();

        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();

        for(Entry<String, JsonValue> entry : entries) {
            // System.out.println(entry.getKey() + " > " + entry.getValue().toString());
            System.out.println(entry.getKey() + " > " + entry.getValue().asJsonObject().getString("country"));
            countries.add(new Country(entry.getKey(), entry.getValue().asJsonObject().getString("country")));
        }
        
        model.addAttribute("countrylist", entries);
        return "countrylist";
    }

    @GetMapping(path = "/countrysearch")
    public String countrySearchForm(@RequestParam String name, Model model) {
        ResponseEntity<String> result = processSvc.filterCountries(name);

        return "result";
    }
}
