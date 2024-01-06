package nus.iss.ssfassessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class SsfAssessmentApplication implements CommandLineRunner{

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private String redisPort;

	@Autowired @Qualifier("orderDatabase")
	RedisTemplate<String, String> template;

	public static void main(String[] args) {
		SpringApplication.run(SsfAssessmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("Redis running on %s@%s\n", redisHost, redisPort);
		System.out.println(template);
	}

}
