package com.blogsystem.cache;

import com.blogsystem.cache.json.JsonRedisFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CacheUtil {
    private final JsonRedisFactory redis;

    public void saveOTPRegistration(String email, String otp) {
        redis.put(CacheConstant.REGISTRATION_OTP_KEY + email, otp, Duration.ofSeconds(240));
    }

    public String getOTPRegistration(String email) {
        return redis.getAsMono(CacheConstant.REGISTRATION_OTP_KEY + email, String.class);
    }

    public void deleteOTPRegistration(String email) {
        redis.evict(CacheConstant.REGISTRATION_OTP_KEY + email, String.class);
    }

    public void refillBucket() {
        redis.getAllKeys(CacheConstant.RATE_LIMIT_KEY + "*")
                .forEach(key -> {
                    var tokens = redis.getAsMono(key, Long.class);
                    if (tokens != null && tokens <= CacheConstant.CAPACITY) {
                        redis.put(key, tokens + CacheConstant.REFILL_CAPACITY >= CacheConstant.CAPACITY
                                ? CacheConstant.CAPACITY
                                : tokens + CacheConstant.REFILL_CAPACITY);
                    }
                });
    }

    public Long getBucket(String key) {
        return redis.getAsMono(CacheConstant.RATE_LIMIT_KEY + key, Long.class);
    }

    public void saveBucket(String key, Long capacity) {
        redis.put(CacheConstant.RATE_LIMIT_KEY + key, capacity);
    }
}
