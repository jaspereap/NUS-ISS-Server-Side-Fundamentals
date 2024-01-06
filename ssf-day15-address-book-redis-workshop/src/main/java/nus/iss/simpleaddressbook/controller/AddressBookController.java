package nus.iss.simpleaddressbook.controller;


// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import nus.iss.simpleaddressbook.model.Contact;
import nus.iss.simpleaddressbook.repository.ContactsRepository;
import nus.iss.simpleaddressbook.utility.Contacts;

@Controller
@RequestMapping(path="/")
public class AddressBookController {

    @Autowired
    private ContactsRepository contactsRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("contact", new Contact());

        return "form";
    }

    // Task 5
    // Retrieve all contacts with link to each contact
    // @GetMapping("/contact")
    // public String getContact(Model model) {
    //     List<Contact> contactList = Contacts.getContactList(dataDir);
    //     model.addAttribute("contactList", contactList);
    //     return "contacts";
    // }

    @GetMapping("/contact")
    public String getContact(Model model) {
        List<Contact> contactList = new LinkedList<>();
        Set<String> keys = contactsRepo.getAllKeys();
        for (String key : keys) {
            // System.out.println("key: " + key);
            Contact contact = contactsRepo.getContact(key).get();
            contactList.add(contact);
        }
        System.out.println(contactList.get(0).toString());
        model.addAttribute("contactList", contactList);
        return "contacts";
    }

    
    @PostMapping("/contact")
    public ModelAndView postContact(@Valid @ModelAttribute Contact contact, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            System.out.println("Error in fields detected!");
            mav.setViewName("form");
            // mav.addObject("contact", new Contact()); -> Error message will reset if instantiate new model here!
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
        if (null != Contacts.checkAge(contact.getDob())) {
            System.out.println("Age error!");
            String errorMessage = Contacts.checkAge(contact.getDob());
            FieldError err = new FieldError("contact","dob", errorMessage);
            result.addError(err);
            mav.setViewName("form");
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
        
        String hexString = Contacts.generateHexString(contact.getName());
        // Contacts.createContact(dataDir, hexString, contact);
        contactsRepo.addContact(hexString, contact);
        
        mav.setViewName("form");
        mav.addObject("contact", new Contact());
        mav.addObject("message", "Contact successfully added!");
        mav.setStatus(HttpStatus.CREATED);
        return mav;
    }

    // Task 4
    // Retrieve Single contact details
    @GetMapping("/contact/{id}")
    public ModelAndView getContact(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();

        // Check if id exists in database, return error accordingly
        // String filePath = dataDir + "/" + id + "/" + id + ".txt";
        // if (!Files.exists(Paths.get(filePath))) {
        //     // return a not found status
        //     System.out.println("Not Found!");
        //     mav.setStatus(HttpStatus.NOT_FOUND);
        //     mav.setViewName("error");
        //     mav.addObject("error", "Contact cannot be found");
        //     mav.addObject("timestamp", LocalDateTime.now());
        //     return mav;
        // }

        if (!contactsRepo.hasId(id)) {
            mav.setStatus(HttpStatus.NOT_FOUND);
            mav.setViewName("error");
            mav.addObject("error", "Contact cannot be found");
            mav.addObject("timestamp", LocalDateTime.now());
            return mav;
        }

        // Retrieve contact
        Optional<Contact> opt = contactsRepo.getContact(id);
        
        Contact contact;
        if (opt.isEmpty()) {
            contact = new Contact();
        } else {
            contact = opt.get();
        }

        // try {
        //     BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        //     String line;
        //     while ((line = br.readLine()) != null) {

        //         String[] tokens = line.split("=");
        //         switch (tokens[0]) {
        //             case "name": contact.setName(tokens[1]); break;
        //             case "email": contact.setEmail(tokens[1]); break;
        //             case "dob": contact.setDob(LocalDate.parse(tokens[1])); break;
        //             case "phone": contact.setPhone(tokens[1]); break;
        //         }
        //     }
        // } catch (FileNotFoundException fnfe) {} catch (IOException ie) {}
        
        

        mav.addObject("contact", contact);
        mav.setViewName("result");
        mav.setStatus(HttpStatus.OK);
        return mav;
    }
}
