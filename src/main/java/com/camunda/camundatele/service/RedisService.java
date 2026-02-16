/*
package com.camunda.camundatele.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Save a value with a timeout (TTL)
    public void saveWithMapping(String correlationId, Long userTaskKey) {
        redisTemplate.opsForValue().set(correlationId, userTaskKey, Duration.ofMinutes(30));
    }

    // Retrieve a value
    public Long getUserTaskKey(String correlationId) {
        Object val = redisTemplate.opsForValue().get(correlationId);
        return val != null ? Long.valueOf(val.toString()) : null;
    }
}
*/
