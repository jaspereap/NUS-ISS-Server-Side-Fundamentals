package nus.iss.news.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import nus.iss.news.Private;
import nus.iss.news.model.Article;
import nus.iss.news.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepo;

    public void cacheArticles(String userCookie, List<Article> articles) {
        newsRepo.saveArticles(userCookie, articles);
    }

    public Optional<List<Article>> getCachedArticles(String userKey) {
        return newsRepo.getArticles(userKey);
    }

    public boolean hasCached(String userCookie) {
        if (!getCachedArticles(userCookie).isEmpty()) {
            return true;
        }
        return false;
    }

    public List<Article> getArticles(String category, String country, Integer show, String userKey) {

        // Cache doesn't exist, retrieving from NewsAPI
        List<Article> articleList = new ArrayList<>();

        JsonObject resultObject = retrieveNewsApi(category, country);
        
        JsonArray articles = resultObject.getJsonArray("articles");

        if (articles.size() < show) {
            show = articles.size();
        }

        for (int i = 0; i < show; i++) {
            Article article = new Article();

            // Validate fields
            JsonObject currArticle = articles.getJsonObject(i);
            
            String title = currArticle.getOrDefault("title",Json.createValue("NONE")).toString();
            String author = currArticle.getOrDefault("author",Json.createValue("NONE")).toString();
            String description = currArticle.getOrDefault("description",Json.createValue("NONE")).toString();
            String timestamp = currArticle.getOrDefault("publishedAt",Json.createValue("NONE")).toString();
            String articleUrl = currArticle.getOrDefault("url",Json.createValue("NONE")).toString();
            String imageUrl = currArticle.getOrDefault("urlToImage",Json.createValue("NONE")).toString();
            article.setTitle(title);
            article.setAuthor(author);
            article.setDescription(description);
            article.setTimestamp(timestamp);
            article.setArticleUrl(articleUrl);
            article.setImageUrl(imageUrl);
            // System.out.println("\tINSERTING NEW ARTICLE: >>>>>> " + article);
            // if (title.equals("null")) {
            //     article.setTitle("NONE");
            // } else {
            //     article.setTitle(title);
            // }
            // if (author.equals("null")) {
            //     article.setAuthor("NONE");
            // } else {
            //     article.setAuthor(author.toString());
            // }
            // if ("null".equals(imageUrl)) {
            //     article.setImageUrl("NONE");
            // } else {
            //     article.setImageUrl(imageUrl);
            // }
            // if (description.equals("null")) {
            //     article.setDescription("NONE");
            // } else {
            //     article.setDescription(description);
            // }
            // if (timestamp.equals("null")) {
            //     article.setTimestamp("NONE");
            // } else {
            //     article.setTimestamp(timestamp);
            // }
            // if (articleUrl.equals("null")) {
            //     article.setArticleUrl("NONE");
            // } else {
            //     article.setArticleUrl(articleUrl);
            // }
            // System.out.println(article);
            articleList.add(article);
        }
        // Cache articles in redis
        cacheArticles(userKey, articleList);
        return articleList;
    }

    public JsonObject retrieveNewsApi(String category, String country) {
        String apiUrl = "https://newsapi.org/v2/top-headlines?";
        String url = apiUrl + "category=" + category + "&country=" + country;
        RestTemplate template = new RestTemplate();
        RequestEntity<Void> request = 
            RequestEntity
            .get(url).header("X-Api-Key", Private.NEWS_API_KEY)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<String> result = template.exchange(request, String.class);
        JsonObject jobj = Json.createReader(new StringReader(result.getBody())).readObject();
        return jobj;
    }

}
