package nus.iss.ssfassessment;

import java.util.Map;

public class PizzaUtils {
    public static final String[] PIZZA = new String[] 
    {"bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio"};

    public static final String[] SIZE = new String[]
    {"sm", "md", "lg"};

    public static final Map<String, Float> PIZZA_PRICE = Map.of(
        "bella", 30f, 
        "marinara", 30f,
        "spianatacalabrese", 30f,
        "margherita", 22f,
        "trioformaggio", 25f
        );
    
    public static final Map<String, Float> SIZE_MULTIPLIER = Map.of(
        "sm", 1f,
        "md", 1.2f,
        "lg", 1.5f
        );
    
    public static final Map<String, Float> OTHER_PRICE = Map.of(
        "rush", 2f
    );
}
