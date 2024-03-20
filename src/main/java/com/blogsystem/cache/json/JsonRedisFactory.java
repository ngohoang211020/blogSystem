package com.blogsystem.cache.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class JsonRedisFactory {
    private static final Logger logger = LoggerFactory.getLogger(JsonRedisFactory.class);

    private final ConcurrentMap<Class<?>, RedisTemplate<String, Object>> templates = new ConcurrentHashMap<>();

    private final RedisConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;

    public JsonRedisFactory(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
    }

    public <T> void put(String key, T data, Duration expiration) {
        if (data == null) {
            logger.warn("No data was given. Cancelling cache for key [{}]", key);
            return;
        }

        RedisTemplate<String, Object> template = getRedisTemplate(data.getClass());

        template.opsForValue().set(key, data, expiration);
        logger.info("Inserted/Updated cache entry for key [{}]", key);
    }

    public <T> void put(String key, List<T> items, Duration expiration) {
        if (CollectionUtils.isEmpty(items)) {
            logger.warn("No data was given. Cancelling cache for key [{}]", key);
            return;
        }

        RedisTemplate<String, Object> template = getRedisTemplate(List.class);

        try {
            template.opsForValue().set(key, objectMapper.writeValueAsString(items), expiration);
            logger.info("Inserted/Updated cache entry for key [{}]", key);
        } catch (JsonProcessingException e) {
            logger.error("Failed to cache list {} items for key [{}]. {}", items.get(0).getClass().getSimpleName(), key, e.getMessage());
        }
    }

    public <T> void putOrThrow(String key, T value, Duration expiration) {
        if (value == null) {
            throw new IllegalArgumentException("Null value was given when trying to cache for key " + key);
        }

        RedisTemplate<String, Object> template = getRedisTemplate(value.getClass());

        template.opsForValue().set(key, value, expiration);
        logger.info("Inserted/Updated cache entry for key [{}]", key);
    }

    public <T> void putOrThrow(String key, List<T> items, Duration expiration) throws JsonProcessingException {
        if (CollectionUtils.isEmpty(items)) {
            logger.warn("No data was given. Cancelling cache for key [{}]", key);
            return;
        }

        RedisTemplate<String, Object> template = getRedisTemplate(List.class);

        template.opsForValue().set(key, objectMapper.writeValueAsString(items), expiration);
        logger.info("Inserted/Updated cache entry for key [{}]", key);
    }


    public <T> T getAsMono(String key, Class<T> clazz) {
        RedisTemplate<String, Object> template = getRedisTemplate(clazz);

        Object cached = template.opsForValue().get(key);

        if (cached == null) {
            logger.info("Cache miss for key [{}]", key);
            return null;
        } else {
            return objectMapper.convertValue(cached, clazz);
        }
    }

    public <T> List<T> getAsList(String key, Class<T> clazz) {
        RedisTemplate<String, Object> template = getRedisTemplate(clazz);

        Object cached = template.opsForValue().get(key);

        if (cached == null) {
            logger.info("Cache miss for key [{}]", key);
            return null;
        }

        try {
            List<Object> raw = objectMapper.readValue(cached.toString(), new TypeReference<>() {
            });
            return raw.stream()
                    .map(s -> objectMapper.convertValue(s, clazz))
                    .collect(Collectors.toUnmodifiableList());
        } catch (Exception e) {
            logger.error("Failed to read data from cache for key [{}]. {}", key, e.getMessage());
            return null;
        }
    }

    public <T> boolean has(String key, Class<T> clazz) {
        RedisTemplate<String, Object> template = getRedisTemplate(clazz);

        return Boolean.TRUE.equals(template.hasKey(key));
    }

    public <T> boolean notHas(String key, Class<T> clazz) {
        return !has(key, clazz);
    }

    public <T> void evict(String key, Class<T> clazz) {
        RedisTemplate<String, Object> template = getRedisTemplate(clazz);

        template.delete(key);
    }

    /**
     * @param key                Cached key
     * @param clazz              Type of cached value
     * @param checkpointInSecond Must greater than zero
     * @return true if ttl <= checkpointInSecond
     * @implSpec https://redis.io/commands/ttl
     * @implSpec https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisOperations.html#getExpire-K-
     */
    public <T> boolean isAboutToBeExpired(String key, Class<T> clazz, long checkpointInSecond) {
        RedisTemplate<String, Object> template = getRedisTemplate(clazz);

        Long ttlInSecond = template.getExpire(key);

        if (ttlInSecond == null || ttlInSecond <= -2) {
            return true;
        } else if (ttlInSecond == -1) {
            logger.warn("Cache entry with key = {} has no expiration set", key);
            return false;
        }

        return ttlInSecond <= checkpointInSecond;
    }

    private <T> RedisTemplate<String, Object> getRedisTemplate(Class<T> clazz) {
        RedisTemplate<String, Object> template = templates.get(clazz);

        if (template == null) {
            template = buildGenericJacksonRedisTemplate();
            templates.put(clazz, template);
        }

        return template;
    }

    protected RedisTemplate<String, Object> buildGenericJacksonRedisTemplate() {
        RedisSerializer<?> valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setEnableDefaultSerializer(false);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
