package poomasi.domain.auth.reissue.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import poomasi.global.error.BusinessException;
import poomasi.global.redis.service.RedisService;

import java.time.Duration;
import java.util.List;

import static poomasi.global.error.BusinessError.REFRESH_TOKEN_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

    private final RedisService redisService;

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    public void putRefreshToken(final String refreshToken, Long memberId) {
        String redisKey = generateKey(memberId, refreshToken);
        redisService.setValues(redisKey, memberId.toString(), Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public Long getRefreshToken(final String refreshToken, Long memberId) {
        String redisKey = generateKey(memberId, refreshToken);
        String result = redisService.getValues(redisKey)
                .orElseThrow(() -> new BusinessException(REFRESH_TOKEN_NOT_FOUND));
        return Long.parseLong(result);
    }

    public void removeMemberRefreshToken(final Long memberId) {
        List<String> keys = redisService.scanKeysByPattern(generateKey(memberId, "*"));
        for (String key : keys) {
            redisService.deleteValues(key);
        }
    }

    private String generateKey(Long memberId, String token) {
        return "refreshToken:" + memberId + ":" + token;
    }

}