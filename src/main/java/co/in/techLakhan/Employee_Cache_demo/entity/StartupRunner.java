package co.in.techLakhan.Employee_Cache_demo.entity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final RedisTemplate<String, Object> redisTemplate;

    public StartupRunner(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Redis is connected & running successfully.");
    }
}
