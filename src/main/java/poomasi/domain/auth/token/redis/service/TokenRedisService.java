package poomasi.domain.auth.token.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.token.redis.error.RedisConnectionException;
import poomasi.domain.auth.token.redis.error.RedisOperationException;
import poomasi.domain.auth.token.refreshtoken.service.TokenStorageService;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenRedisService implements TokenStorageService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;
    private final RedisConnectionFactory redisConnectionFactory;

    public void setValues(String key, String data, Duration duration) {
        String redisKey = generateKey(data, key);
        handleRedisException(() -> {
            ValueOperations<String, Object> values = redisTemplate.opsForValue();
            values.set(redisKey, data, duration);
            return null;
        }, "Redis에 값을 설정하는 중 오류 발생: " + redisKey);
    }

    public Optional<String> getValues(String key, String data) {
        String redisKey = generateKey(data, key);
        return handleRedisException(() -> {
            ValueOperations<String, Object> values = redisTemplate.opsForValue();
            Object result = values.get(redisKey);
            return Optional.ofNullable(result).map(Object::toString);
        }, "Redis에서 값을 가져오는 중 오류 발생: " + redisKey);
    }

    public void removeRefreshTokenById(Long memberId) {
        List<String> keys = scanKeysByPattern(generateKey(String.valueOf(memberId), "*"));
        for (String key : keys) {
            deleteValues(key, memberId.toString());
        }
    }

    public void deleteValues(String key, String data) {
        String redisKey = generateKey(data, key);
        handleRedisException(() -> redisTemplate.delete(redisKey), "Redis에서 값을 삭제하는 중 오류 발생: " + redisKey);
    }

    public List<String> scanKeysByPattern(String pattern) {
        return handleRedisException(() -> {
            List<String> keys = new ArrayList<>();
            ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();

            try (RedisConnection connection = redisConnectionFactory.getConnection()) {
                Cursor<byte[]> cursor = connection.scan(options);
                while (cursor.hasNext()) {
                    keys.add(new String(cursor.next()));
                }
            } catch (Exception e) {
                throw new RedisOperationException("Redis SCAN 중 오류 발생");
            }
            return keys;
        }, "SCAN 중 오류 발생: " + pattern);
    }

    public boolean hasKey(String key, String data) {
        String redisKey = generateKey(data, key);
        return handleRedisException(() -> Boolean.TRUE.equals(redisTemplate.hasKey(redisKey)), "Redis에서 키 존재 여부 확인 중 오류 발생: " + redisKey);
    }

    public List<String> getKeysByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys != null ? new ArrayList<>(keys) : Collections.emptyList();
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

    private String generateKey(String memberId, String token) {
        return "refreshToken:" + memberId + ":" + token;
    }

}