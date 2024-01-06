package nus.iss.day15.redis.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

import nus.iss.day15.redis.cart.repository.CartRepository;
import nus.iss.day15.redis.cart.services.CartService;

@SpringBootApplication
public class Application implements	CommandLineRunner{

	@Autowired
	CartRepository cartRepo;

	@Autowired
	CartService cartSvc;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) {
		System.out.println(cartRepo);
		System.out.println(cartSvc);
	}

}
