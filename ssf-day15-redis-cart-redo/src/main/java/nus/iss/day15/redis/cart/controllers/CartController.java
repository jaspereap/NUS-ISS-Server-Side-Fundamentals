package nus.iss.day15.redis.cart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nus.iss.day15.redis.cart.model.Item;
import nus.iss.day15.redis.cart.services.CartService;

@Controller
@RequestMapping(path = "/cart")
public class CartController {
    
    @Autowired
    private CartService cartSvc;

    @GetMapping
    public String getCart(@RequestParam String name, HttpSession sess, Model model) {
        System.out.println(name);
        // get cart using business logic from CartService
        List<Item> cart = cartSvc.getCart(name);

        // save cart to current session
        sess.setAttribute("cart", cart);
        // save user name to current session
        sess.setAttribute("user", name);

        // add cart to model for view
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping
    public String postCart(@ModelAttribute Item item, Model model, HttpSession sess) {
        System.out.println(item.getName());
        System.out.println(item.getQuantity());

        // Retrieve cart from current session
        List<Item> cart = (List<Item>) sess.getAttribute("cart");
        // Append item to current cart
        cart.add(item);

        // add cart to model for view
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping(path="/checkout")
    public String postCartCheckout(HttpSession sess) {
        // Retrieve user name and cart from session
        String username = (String) sess.getAttribute("user");
        List<Item> cart = (List<Item>) sess.getAttribute("cart");

        // Save cart to redis
        cartSvc.saveCart(username, cart);
        // Destroy session
        sess.invalidate();
        
        return "redirect:/";
    }
}
