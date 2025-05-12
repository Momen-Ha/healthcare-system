package ppu.momen.healthcare.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ppu.momen.healthcare.model.AbnormalReading;

import java.util.Collections;
import java.util.List;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheAbnormalReadings(String patientNumber, List<AbnormalReading> readings) {
        String key = String.format("abnormal:%s", patientNumber);
        redisTemplate.opsForValue().set(key, readings);
    }

    @SuppressWarnings("unchecked")
    public List<AbnormalReading> getAbnormalReadings(String patientNumber) {
        String key = String.format("abnormal:%s", patientNumber);
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return Collections.emptyList();
        }

        try {
            return (List<AbnormalReading>) value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Stored data is not a valid list of abnormal readings", e);
        }
    }

}
