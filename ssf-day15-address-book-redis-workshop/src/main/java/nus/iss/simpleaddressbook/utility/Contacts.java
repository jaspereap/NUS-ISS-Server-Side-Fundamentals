package nus.iss.simpleaddressbook.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.cglib.core.Local;

import nus.iss.simpleaddressbook.model.Contact;

public class Contacts {

    // public static void createContact(String dataDir, String hex, Contact contact) {
    //     Path contactFile = createEmptyContact(dataDir, hex);
    //     try {
            
    //         PrintWriter pw = new PrintWriter(contactFile.toFile());
    //         pw.println("name="+contact.getName());
    //         pw.println("email="+contact.getEmail());
    //         pw.println("dob="+contact.getDob());
    //         pw.println("phone="+contact.getPhone());
    //         pw.flush();
            
    //     } catch (IOException ie) {}
    // }
    // Creates directory and txt file
    // public static Path createEmptyContact(String dataDir, String hex) {
    //     Path directoryPath = Paths.get(dataDir + "/"+ hex);
    //     Path contactFile = Paths.get(dataDir + "/" + hex + "/" + hex + ".txt");
    //     try {
    //         Files.createDirectory(directoryPath);
    //         Files.createFile(contactFile);
    //     } catch (IOException ie) {}
    //     return contactFile;
    // }

    public static String generateHexString(String name) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        // Generate random hex string
        // for (int i = 0; i < 4; i++) {
        //     sb.append(name.charAt(rand.nextInt(name.length())));
        // }
        sb.append(name.substring(0, 3));
        for (int i = 0; i < 4; i++) {
            sb.append(rand.nextInt(9));
        }
        String hexString = sb.toString();

        return hexString;
    }

    public static String checkAge(LocalDate dob) {
        // Calculate age
        Integer age = LocalDate.now().getYear() - dob.getYear();
        System.out.println(">>> User's age: " + age);

        // Logic
        if (age < 10) {
            return "You must be older than 10";
        } else if (age > 100) {
            return "You cannot be older than 100";
        }
        return null;
    }

    // public static List<Contact> getContactList(String dataDir) {
    //     List<Contact> contactList = new LinkedList<>();

    //     Path contactDir = Paths.get(dataDir);

    //     try {
    //         DirectoryStream<Path> paths = Files.newDirectoryStream(contactDir);
    //         for (Path p : paths) {

    //             String userId = p.toString().replaceFirst(dataDir + "/", "");

    //             Path contactPath = Paths.get(p + "/" + userId + ".txt");
                
    //             Contact contact = new Contact();
        
    //             BufferedReader br = Files.newBufferedReader(contactPath);
    //             String line;
    //             while ((line = br.readLine()) != null) {

    //                 String[] tokens = line.split("=");
    //                 contact.setHexString(userId);
    //                 switch (tokens[0]) {
    //                     case "name": contact.setName(tokens[1]); break;
    //                     case "email": contact.setEmail(tokens[1]); break;
    //                     case "dob": contact.setDob(LocalDate.parse(tokens[1])); break;
    //                     case "phone": contact.setPhone(tokens[1]); break;
    //                 }
    //             }

    //             contactList.add(contact);
    //         }
    //     } catch (Exception e) {}
        
    //     return contactList;
    // }

}
