package nus.iss.simpleaddressbook.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.simpleaddressbook.model.Contact;

@Repository
public class ContactsRepository {

    @Autowired @Qualifier("ContactsRedis")
    private RedisTemplate<String, String> template;

    // Check if key exists
    public boolean hasId(String id) {
        return template.hasKey(id);
    }

    // Get all keys
    public Set<String> getAllKeys() {

        Set<String> keys = template.keys("*");

        return keys;
    }

    // Retrieve a contact
    public Optional<Contact> getContact(String userId) {
        String name = (String) template.opsForHash().get(userId, "name");
        String email = (String) template.opsForHash().get(userId, "email");
        LocalDate dob = LocalDate.parse((String)template.opsForHash().get(userId, "dob"));
        String phone = (String) template.opsForHash().get(userId, "phone");
        
        System.out.printf("ContactRepo/getContact: %s, %s, %s, %s\n", name, email, dob, phone);
        Contact contact = new Contact(name, email, phone, dob, userId);
        
        return Optional.ofNullable(contact);
    }

    // Add a contact
    public void addContact(String userId, Contact contact) {

        template.opsForHash().put(userId, "name", contact.getName());
        template.opsForHash().put(userId, "email", contact.getEmail());
        template.opsForHash().put(userId, "dob", contact.getDob().toString());
        template.opsForHash().put(userId, "phone", contact.getPhone());

    }
}
