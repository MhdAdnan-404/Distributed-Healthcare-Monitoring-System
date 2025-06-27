package com.example.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CoolDownService {
    private final long COOLDOWN_MS = 2 * 60 * 1000;
    private final String PREFIX = "vitalAlertCooldown";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean isInCooldown(String Email){
        String key = PREFIX + Email;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void addToCooldown(String patientId) {
        String key = PREFIX + patientId;
        redisTemplate.opsForValue().set(key, "cooling", COOLDOWN_MS, TimeUnit.MILLISECONDS);
    }


}
