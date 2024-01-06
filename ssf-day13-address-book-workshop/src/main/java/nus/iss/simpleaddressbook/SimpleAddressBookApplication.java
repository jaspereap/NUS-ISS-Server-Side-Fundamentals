package nus.iss.simpleaddressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleAddressBookApplication {

	public static void main(String[] args) {
		String dataDir = "";
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);
		if (cliOpts.containsOption("dataDir")) {
			dataDir = cliOpts.getOptionValues("dataDir").get(0);
		} else {
			System.out.println("--dataDir option is not specified");
			return;
		}
		System.out.println("dataDir: " + dataDir);

		Path dataDirPath = Paths.get(dataDir);

		if (!Files.exists(dataDirPath)) {
			try {
				Files.createDirectory(dataDirPath);
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
		
		// SpringApplication.run(SimpleAddressBookApplication.class, args);
		SpringApplication app = new SpringApplication(SimpleAddressBookApplication.class);
		app.run(args);

	}

}
