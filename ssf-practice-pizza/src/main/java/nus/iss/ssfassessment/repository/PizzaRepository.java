package nus.iss.ssfassessment.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaRepository {

    @Autowired @Qualifier("orderDatabase")
    private RedisTemplate<String, String> template;

    public void saveOrder(String orderId, String value) {
        template.opsForValue().set(orderId, value);
    }

    public Optional<String> getOrder(String orderId) {
        String result = template.opsForValue().get(orderId);
        return Optional.ofNullable(result);
    }
}
