package poomasi.domain.auth.token.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.token.blacklist.service.TokenBlacklistService;

import java.time.Duration;
import java.util.*;

import static poomasi.domain.auth.token.redis.error.RedisExceptionHandler.handleRedisException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlacklistRedisService implements TokenBlacklistService {
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    @Transactional
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

    @Transactional
    public void deleteBlackList(String key) {
        handleRedisException(() -> redisBlackListTemplate.delete(key), "블랙리스트에서 값을 삭제하는 중 오류 발생: " + key);
    }

    public boolean hasKeyBlackList(String key) {
        return handleRedisException(() -> Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key)), "블랙리스트에서 키 존재 여부 확인 중 오류 발생: " + key);
    }

}