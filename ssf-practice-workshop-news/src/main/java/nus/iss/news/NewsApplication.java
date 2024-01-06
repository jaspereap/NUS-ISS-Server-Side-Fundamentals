package nus.iss.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nus.iss.news.model.Article;
import nus.iss.news.repository.NewsRepository;

@SpringBootApplication
public class NewsApplication implements CommandLineRunner{
	@Autowired
	NewsRepository newsRepo;

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}

	@Value("${spring.redis.port}")
	String redisPort;
	@Value("${spring.redis.host}")
	String redisHost;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(newsRepo);
		System.out.println("Redis Host: " + redisHost);
		System.out.println("Running on port " + redisPort);

		// List<Article> articles = new ArrayList<>();
		// Article article = new Article();
		// article.setTitle("title");
		// article.setImageUrl("wwww.image.com");
		// article.setArticleUrl("www.url.com");
		// article.setDescription("hahaha");
		// article.setTimestamp("22-22-22");
		// article.setAuthor("jasper");
		// articles.add(article);

		// newsRepo.saveArticles("jasper", articles);

		// List<Article> retrievedList = newsRepo.getArticles("jasper").get();
		// System.out.println(retrievedList);
	}

}
