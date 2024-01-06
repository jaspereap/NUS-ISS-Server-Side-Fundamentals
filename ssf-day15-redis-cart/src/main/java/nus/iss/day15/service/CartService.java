package nus.iss.day15.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import nus.iss.day15.redis.cart.model.Item;
import nus.iss.day15.redis.cart.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;
    
    public List<Item> getCart(String name) {
        if (cartRepo.hasCart(name)) {
            // return cart
            return cartRepo.getCart(name);
        } 
        return new LinkedList<Item>();
    }

    public void save(String name, List<Item> cart) {
        cartRepo.addCart(name, cart);
        cartRepo.deleteCart(name);
    }
}
