package nus.iss.day15.redis.cart.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.day15.redis.cart.model.Item;
import nus.iss.day15.redis.cart.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;
    
    public List<Item> getCart(String name) {
        List<Item> cart;
        if (cartRepo.hasCart(name)) {
            System.out.println("User cart exists, retrieving cart from redis!");
            cart = cartRepo.getCart(name);
        } else {
            // Else, create a new empty cart
            cart = new LinkedList<>();
        }
        return cart;
    }

    public void saveCart(String name, List<Item> cart) {
        cartRepo.deleteCart(name);
        cartRepo.saveCart(name, cart);
    }
}
