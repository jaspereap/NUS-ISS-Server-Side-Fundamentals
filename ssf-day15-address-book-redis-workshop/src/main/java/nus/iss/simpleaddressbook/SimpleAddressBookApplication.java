package nus.iss.simpleaddressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import nus.iss.simpleaddressbook.model.Contact;
import nus.iss.simpleaddressbook.repository.ContactsRepository;

@SpringBootApplication
public class SimpleAddressBookApplication implements CommandLineRunner {

	@Autowired @Qualifier("ContactsRedis")
	private RedisTemplate<String, String> template;

	@Autowired
	private ContactsRepository contactsRepo;

	public static void main(String[] args) {
		// String dataDir = "";
		// ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		// if (cliOpts.containsOption("dataDir")) {
		// 	dataDir = cliOpts.getOptionValues("dataDir").get(0);
		// } else {
		// 	System.out.println("--dataDir option is not specified");
		// 	return;
		// }
		// System.out.println("dataDir: " + dataDir);

		// Path dataDirPath = Paths.get(dataDir);

		// if (!Files.exists(dataDirPath)) {
		// 	try {
		// 		Files.createDirectory(dataDirPath);
		// 	} catch (IOException ie) {
		// 		ie.printStackTrace();
		// 	}
		// }
		
		SpringApplication.run(SimpleAddressBookApplication.class, args);
		// SpringApplication app = new SpringApplication(SimpleAddressBookApplication.class);
		// app.run(args);

	}

	@Override
	public void run(String... args) throws Exception {
		if (template != null) {
			System.out.println("Successfully connected to Redis server");
		}
		if (contactsRepo != null) {
			System.out.println("Successfully connected to ContactsRepository");
		}

		// Contact newContact = new Contact();
		// newContact.setName("jas");
		// newContact.setEmail("jas@gmail.com");
		// newContact.setPhone("1234567");
		// newContact.setDob(LocalDate.parse("1997-02-22"));
		// newContact.setDob(LocalDate.parse("22/02/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		// System.out.println(newContact.getDob());
		// contactsRepo.addContact("1", newContact);
		// contactsRepo.getContact("1");
		// System.out.println(contactsRepo.getAllKeys());

	}

}
