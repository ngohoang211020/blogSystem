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
        redis.put(CacheKey.REGISTRATION_OTP_KEY + email, otp, Duration.ofSeconds(240));
    }

    public String getOTPRegistration(String email) {
        return redis.getAsMono(CacheKey.REGISTRATION_OTP_KEY + email, String.class);
    }

    public void deleteOTPRegistration(String email){
        redis.evict(CacheKey.REGISTRATION_OTP_KEY+email,String.class);
    }
}
