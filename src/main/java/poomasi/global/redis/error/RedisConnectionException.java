package poomasi.global.redis.error;

public class RedisConnectionException extends RuntimeException {
    public RedisConnectionException(String message) {
        super(message);
    }
}