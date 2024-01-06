package nus.iss.d17lecture.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nus.iss.d17lecture.service.ProcessService;

@RestController
@RequestMapping(path = "/process")
public class ProcessController {
    
    @Autowired
    ProcessService processSvc;

    @PostMapping(path = "/searchBook", produces = "application/json")
    public String processBookSearch(@RequestBody MultiValueMap<String, String> form, Model Model) {
        String author = form.getFirst("author");
        System.out.println(author);

        String result = processSvc.searchBook(author);

        return result;
    }

    public String processCountrySearch(@RequestBody MultiValueMap<String, String> form) {
        
        ResponseEntity<String> results = processSvc.filterCountries(form.getFirst("searchName"));

        return results.getBody();
    }

}
