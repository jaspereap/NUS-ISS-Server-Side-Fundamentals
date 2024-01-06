package nus.iss.day19work.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepo {
    @Autowired @Qualifier("redisTemplate")
    private RedisTemplate<String, String> template;


    
}
