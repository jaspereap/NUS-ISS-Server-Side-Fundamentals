package nus.iss.day15.redis.cart.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.day15.redis.cart.Utils;
import nus.iss.day15.redis.cart.model.Item;

@Repository
public class CartRepository {

    @Autowired @Qualifier(Utils.BEAN_REDIS)
    private RedisTemplate<String, String> template;
    
    public boolean hasCart(String name) {
        return template.hasKey(name);
    }

    public void deleteCart(String name) {
        template.delete(name);
    }

    public void addCart(String name, List<Item> cart) {
        ListOperations<String, String> list = template.opsForList();
        cart.stream()
           .forEach(item -> {
              String rec = "%s,%d".formatted(item.getName(), item.getQuantity());
              list.leftPush(name, rec);
           });
     }

    public List<Item> getCart(String name) {
        // name - string, quantity - integer
        ListOperations<String, String> list = template.opsForList();
        Long size = list.size(name);
        List<Item> cart = new LinkedList<>();
        for (String s : list.range(name, 0, size)) {
            String[] terms = s.split(",");
            Item item = new Item();
            item.setName(terms[0]);
            item.setQuantity(Integer.parseInt(terms[1]));
            cart.add(item);
        }
        return cart;
    }



    }


