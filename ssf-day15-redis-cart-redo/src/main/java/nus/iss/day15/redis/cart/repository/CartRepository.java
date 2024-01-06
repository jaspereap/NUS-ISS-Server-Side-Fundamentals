package nus.iss.day15.redis.cart.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.day15.redis.cart.model.Item;

@Repository
public class CartRepository {

    @Autowired @Qualifier("redis")
    private RedisTemplate<String, String> template;

    public boolean hasCart(String name) {
        return template.hasKey(name);
    }

    public void deleteCart(String name) {
        template.delete(name);
    }

    // Save as {"user": ["apple,2", "orange,3", ...]}
    public void saveCart(String name, List<Item> cart) {
        deleteCart(name);
        for (Item i : cart) {
            template.opsForList().leftPush(name, i.getName()+","+i.getQuantity());
        }
    }
    // Unpack "apple,2" into "apple" and "2"
    public List<Item> getCart(String name) {
        List<Item> cart = new LinkedList<>();

        for (int i = 0; i < template.opsForList().size(name); i++) {
            // iterate each item in list
            String nameQuantity = template.opsForList().index(name, i);
   
            String[] tokens = nameQuantity.split(",");
            Item item = new Item();
            item.setName(tokens[0]);
            item.setQuantity(tokens[1]);

            cart.add(item);
        }
        return cart;
    }



}
