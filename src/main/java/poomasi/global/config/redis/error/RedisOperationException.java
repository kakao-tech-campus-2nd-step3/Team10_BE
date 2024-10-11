package poomasi.global.config.redis.error;

public class RedisOperationException extends RuntimeException {
    public RedisOperationException(String message) {
        super(message);
    }
}