package ppu.momen.healthcare.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheAbnormalReading(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getAbnormalReading(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
