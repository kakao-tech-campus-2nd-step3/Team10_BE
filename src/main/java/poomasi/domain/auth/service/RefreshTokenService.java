package poomasi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.auth.entity.RefreshToken;
import poomasi.global.error.BusinessException;
import poomasi.global.util.JwtProvider;

import java.util.HashMap;
import java.util.Map;

import static poomasi.global.error.BusinessError.REFRESH_TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtProvider jwtProvider;
    private final RefreshToken refreshTokenManager;

    public TokenResponse refreshToken(final String refreshToken) {

        checkRefreshToken(refreshToken);

        Long id = refreshTokenManager.getRefreshToken(refreshToken);

        String email = jwtProvider.getSubjectFromToken(refreshToken);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id",id);
        String newAccessToken = jwtProvider.generateAccessToken(email, claims);

        refreshTokenManager.removeRefreshToken(refreshToken);

        String newRefreshToken = jwtProvider.generateRefreshToken(email);
        refreshTokenManager.putRefreshToken(newRefreshToken, id);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private void checkRefreshToken(final String refreshToken) {
        if(Boolean.FALSE.equals(jwtProvider.validateToken(refreshToken)))
            throw new BusinessException(REFRESH_TOKEN_NOT_FOUND);
    }
}
