package poomasi.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import poomasi.global.redis.error.RedisConnectionException;
import poomasi.global.redis.error.RedisOperationException;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public void setValues(String key, String data, Duration duration) {
        handleRedisException(() -> {
            ValueOperations<String, Object> values = redisTemplate.opsForValue();
            values.set(key, data, duration);
            return null;
        }, "Redis에 값을 설정하는 중 오류 발생: " + key);
    }

    public Optional<String> getValues(String key) {
        return handleRedisException(() -> {
            ValueOperations<String, Object> values = redisTemplate.opsForValue();
            Object result = values.get(key);
            return Optional.ofNullable(result).map(Object::toString);
        }, "Redis에서 값을 가져오는 중 오류 발생: " + key);
    }

    public void deleteValues(String key) {
        handleRedisException(() -> redisTemplate.delete(key), "Redis에서 값을 삭제하는 중 오류 발생: " + key);
    }

    public boolean hasKey(String key) {
        return handleRedisException(() -> Boolean.TRUE.equals(redisTemplate.hasKey(key)), "Redis에서 키 존재 여부 확인 중 오류 발생: " + key);
    }

    public void setBlackList(String key, String data, Duration duration) {
        handleRedisException(() -> {
            ValueOperations<String, Object> values = redisBlackListTemplate.opsForValue();
            values.set(key, data, duration);
            return null;
        }, "블랙리스트에 값을 설정하는 중 오류 발생: " + key);
    }

    public Optional<String> getBlackList(String key) {
        return handleRedisException(() -> {
            ValueOperations<String, Object> values = redisBlackListTemplate.opsForValue();
            Object result = values.get(key);
            return Optional.ofNullable(result).map(Object::toString);
        }, "블랙리스트에서 값을 가져오는 중 오류 발생: " + key);
    }

    public void deleteBlackList(String key) {
        handleRedisException(() -> redisBlackListTemplate.delete(key), "블랙리스트에서 값을 삭제하는 중 오류 발생: " + key);
    }

    public boolean hasKeyBlackList(String key) {
        return handleRedisException(() -> Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key)), "블랙리스트에서 키 존재 여부 확인 중 오류 발생: " + key);
    }

    public List<String> getKeysByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys != null ? new ArrayList<>(keys) : Collections.emptyList();
    }

    private <T> T handleRedisException(Supplier<T> action, String errorMessage) {
        try {
            return action.get();
        } catch (RedisConnectionException e) {
            log.error(errorMessage, e);
            throw new RedisConnectionException(errorMessage);
        } catch (Exception e) {
            log.error(errorMessage, e);
            throw new RedisOperationException(errorMessage);
        }
    }

}