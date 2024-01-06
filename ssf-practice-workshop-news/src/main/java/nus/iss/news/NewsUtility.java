package nus.iss.news;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import nus.iss.news.model.Country;

public class NewsUtility {
    public static List<Country> getCountryList(String[] countryCode) {
        List<Country> countryList = new ArrayList<>();

        // countryCode = new String[] {"sg", "kr"};
        String api_url = "https://restcountries.com/v3.1/alpha?codes=";

        StringBuilder sb = new StringBuilder();
        sb.append(api_url);
        for (String code : countryCode) {
            sb.append(code).append(",");
        }
        String call_url = sb.toString();
        String final_url = call_url.substring(0,call_url.length() - 1);
        System.out.println("Sending call to: " + final_url);
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> response = template.getForEntity(final_url, String.class);
        JsonArray arr = Json.createReader(new StringReader(response.getBody())).readArray();
        for (int i = 0; i < arr.size(); i++) {
            Country country = new Country();
            String name = arr.getJsonObject(i).getJsonObject("name").getString("common");
            String code = arr.getJsonObject(i).getString("cca2");
            country.setName(name);
            country.setCode(code);
            countryList.add(country);
        }

        return countryList;
    }

    public static void printList(List<?> list) {
        for (Object element : list) {
            System.out.println(element);
        }
    }

    public final static String[] supportedCountryCodes = new String[] 
        {"ae","ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co",
        "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id",
        "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx",
        "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs",
        "ru", "sa", "se", "sg", "si", "sk", "th" , "tr", "tw", "ua",
        "us", "ve", "za"};
    
    public final static String[] supportedCategories = new String[] 
    {"Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};
}
