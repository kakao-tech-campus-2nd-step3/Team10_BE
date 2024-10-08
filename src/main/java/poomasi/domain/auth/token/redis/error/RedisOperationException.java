package poomasi.domain.auth.token.redis.error;

public class RedisOperationException extends RuntimeException {
    public RedisOperationException(String message) {
        super(message);
    }
}