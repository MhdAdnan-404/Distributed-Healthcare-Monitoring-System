package com.mhd.vitalSignConsumer.service;

import com.mhd.vitalSignConsumer.model.Limits.LimitEntry;
import io.lettuce.core.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ListIterator;

@Service
public class DeviceLimitFactory {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String PREFIX = "limitEntry:";

    public LimitEntry getOrCreate(String name, Double value){
        String key = PREFIX + name + "_" + value;

        LimitEntry cached = (LimitEntry) redisTemplate.opsForValue().get(key);

        if(cached != null){
            return cached;
        }

        LimitEntry newEntry = new LimitEntry(name,value);
        redisTemplate.opsForValue().set(key,newEntry);
        return newEntry;
    }

}
