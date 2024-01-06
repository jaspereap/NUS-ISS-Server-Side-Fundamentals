package nus.iss.day15.redis.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nus.iss.day15.redis.cart.Utils;
import nus.iss.day15.redis.cart.model.Item;
import nus.iss.day15.redis.cart.repository.CartRepository;
import nus.iss.day15.service.CartService;

@Controller
@RequestMapping(path="/cart")
public class CartController {

   public static final String ATTR_ITEM = "item";
   public static final String ATTR_CART = "cart";
   public static final String ATTR_USERNAME = "username";

    @Autowired
    private CartService cartSvc;


   //  private Logger logger = Logger.getLogger(CartController.class.getName());

    @GetMapping
    public String getCart(@RequestParam String name, HttpSession sess, Model model) {
        System.out.println(name + " input string received");

        List<Item> cart = cartSvc.getCart(name);
        
        sess.setAttribute(Utils.ATTR_CART, cart);

        // add cart to model attribute
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        model.addAttribute("username", name);
        
        return "cart";
    }

   @PostMapping(path = "/checkout")
   public String postCartCheckout(HttpSession sess, @RequestBody String username) {
        // ModelAndView mav = new ModelAndView("cart");

        List<Item> cart = Utils.getCart(sess);
        System.out.printf("Checking out cart: %s\n", cart);

        cartSvc.save(username, cart);

        sess.invalidate();

        // mav.addObject(ATTR_ITEM, new Item());
        // mav.setStatus(HttpStatusCode.valueOf(200));

        return "redirect:/";
   }

   @PostMapping
   public ModelAndView postCart(@Valid @ModelAttribute(ATTR_ITEM) Item item
         , BindingResult bindings, 
         @RequestParam String username, HttpSession sess) {
      // requestparam pulls one field
      // RequestBody will always give entire payload, only applies for POST method
      System.out.printf("item: %s\n", item);
      System.out.printf("error: %b\n", bindings.hasErrors());

      ModelAndView mav = new ModelAndView("cart");

      if (bindings.hasErrors()) {
         mav.setStatus(HttpStatusCode.valueOf(400));
         return mav;
      }
      
      List<Item> cart = Utils.getCart(sess);
      cart.add(item);

      mav.addObject(ATTR_ITEM, new Item());
      mav.addObject(ATTR_CART, cart);
      mav.addObject(ATTR_USERNAME, username);

      mav.setStatus(HttpStatusCode.valueOf(200));
      return mav;
   }
   
}