package poomasi.domain.auth.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import poomasi.global.error.BusinessException;
import poomasi.global.redis.service.RedisService;

import java.time.Duration;
import java.util.Optional;

import static poomasi.global.error.BusinessError.REFRESH_TOKEN_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RefreshToken {

    private final RedisService redisService;

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    public void putRefreshToken(final String refreshToken, Long id) {
        redisService.setValues(refreshToken, id.toString(), Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public Long getRefreshToken(final String refreshToken) {
        String result = redisService.getValues(refreshToken);
        return Optional.ofNullable(result != null ? Long.parseLong(result) : null)
                .orElseThrow(() -> new BusinessException(REFRESH_TOKEN_NOT_FOUND));
    }

    public void removeRefreshToken(final String refreshToken) {
        redisService.deleteValues(refreshToken);
    }

}