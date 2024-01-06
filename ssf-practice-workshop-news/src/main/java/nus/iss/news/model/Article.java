package nus.iss.news.model;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String imageUrl;
    private String author;
    private String description;
    private String timestamp;
    private String articleUrl;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title.replaceAll("\"", "");
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.replaceAll("\"", "");
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author.replaceAll("\"", "");
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description.replaceAll("\"", "");
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp.replaceAll("\"", "");
    }
    public String getArticleUrl() {
        return articleUrl;
    }
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl.replaceAll("\"", "");
    }
    @Override
    public String toString() {
        return "\nArticle [title=" + title + ", \nimageUrl=" + imageUrl + ", \nauthor=" + author + ", \ndescription="
                + description + ", \ntimestamp=" + timestamp + ", \narticleUrl=" + articleUrl + "]";
    }

}
