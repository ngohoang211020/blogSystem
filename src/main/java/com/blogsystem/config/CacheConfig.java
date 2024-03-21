package com.blogsystem.config;

import com.blogsystem.cache.json.JsonRedisFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;


@Configuration
public class CacheConfig {
    @Bean
    public JsonRedisFactory jsonRedisFactory(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        return new JsonRedisFactory(factory, objectMapper);
    }
}
