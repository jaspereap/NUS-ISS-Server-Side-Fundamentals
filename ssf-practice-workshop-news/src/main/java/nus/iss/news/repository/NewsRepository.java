package nus.iss.news.repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.news.model.Article;

@Repository
public class NewsRepository {
    @Autowired @Qualifier("redisTemplate")
    private RedisTemplate<String, List<Article>> template;
    
    public void saveArticles(String key, List<Article> articles) {
        template.opsForValue().set(key, articles, Duration.ofMinutes(30));
    }

    public Optional<List<Article>> getArticles(String key) {
        List<Article> articles = template.opsForValue().get(key);
        return Optional.ofNullable(articles);
    }
}
