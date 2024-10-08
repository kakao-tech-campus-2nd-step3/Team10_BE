package poomasi.domain.auth.token.redis.error;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class RedisExceptionHandler {

    public static <T> T handleRedisException(Supplier<T> action, String errorMessage) {
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