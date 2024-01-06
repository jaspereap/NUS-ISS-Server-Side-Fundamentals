package nus.iss.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import nus.iss.news.NewsUtility;
import nus.iss.news.model.Article;
import nus.iss.news.model.Country;
import nus.iss.news.service.NewsService;

@Controller
@RequestMapping(path = "/news")
public class NewsController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping
    public String landingPage(Model model) {
    
        List<Country> supportedCountries = NewsUtility.getCountryList(NewsUtility.supportedCountryCodes);

        model.addAttribute("categories", NewsUtility.supportedCategories);
        model.addAttribute("countries", supportedCountries);
        return "landing";
    }

    @GetMapping(path = "/searchresult")
    public String postNews(@RequestParam MultiValueMap<String, String> query, Model model, HttpSession sess) {
        String category = query.getFirst("category");
        String country = query.getFirst("country");
        String show = query.getFirst("show");
        // System.out.println("Category: " + category + " Country: " + country + " show: " + show);
        
        List<Article> retrievedArticles = null;

        if (newsSvc.hasCached(sess.getId()+category+country+show) ) {
            // System.out.println("USER HAS CACHED ARTICLES >>>>>");
            retrievedArticles = newsSvc.getCachedArticles(sess.getId()+category+country+show).get();
            model.addAttribute("message", "From cache");
        } else {
            // System.out.println("USER HAS NO CACHED ARTICLES....");
            retrievedArticles = newsSvc.getArticles(category, country, Integer.parseInt(show), sess.getId()+category+country+show);
        }

        if (retrievedArticles.isEmpty() | retrievedArticles == null) {
            // System.out.println("RETRIVED ARTICLE IS EMPTY....");
            String message = "No news found for %s in %s.".formatted(country, category);
            model.addAttribute("message", message);
            return "searchresult";
        }
        // System.out.println("\t ADDING ARTICLES TO MODEL >>>>>>");
        model.addAttribute("articles", retrievedArticles);
        return "searchresult";
    }
}
