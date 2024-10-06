package poomasi.domain.auth.token.refreshtoken.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.global.error.BusinessException;

import java.time.Duration;

import static poomasi.global.error.BusinessError.REFRESH_TOKEN_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final TokenStorageService tokenStorageService;

    public RefreshTokenService(@Qualifier("tokenRedisService") TokenStorageService tokenStorageService) {
        this.tokenStorageService = tokenStorageService;
    }

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Transactional
    public void putRefreshToken(final String refreshToken, Long memberId) {
        tokenStorageService.setValues(refreshToken, memberId.toString(), Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public Long getRefreshToken(final String refreshToken, Long memberId) {
        String result = tokenStorageService.getValues(refreshToken, memberId.toString())
                .orElseThrow(() -> new BusinessException(REFRESH_TOKEN_NOT_FOUND));
        return Long.parseLong(result);
    }

    @Transactional
    public void removeMemberRefreshToken(final Long memberId) {
        tokenStorageService.removeRefreshTokenById(memberId);
    }
}