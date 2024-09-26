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

        Long MemberId = refreshTokenManager.getRefreshToken(refreshToken);

        String email = jwtProvider.getSubjectFromToken(refreshToken);

        return getTokenResponse(MemberId, email, jwtProvider, refreshTokenManager);
    }

    public static TokenResponse getTokenResponse(Long memberId, String email, JwtProvider jwtProvider, RefreshToken refreshTokenManager) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", memberId);
        String newAccessToken = jwtProvider.generateAccessToken(email, claims);

        refreshTokenManager.removeMemberRefreshToken(memberId);

        String newRefreshToken = jwtProvider.generateRefreshToken(email);
        refreshTokenManager.putRefreshToken(newRefreshToken, memberId);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }


    private void checkRefreshToken(final String refreshToken) {
        if(Boolean.FALSE.equals(jwtProvider.validateToken(refreshToken)))
            throw new BusinessException(REFRESH_TOKEN_NOT_FOUND);
    }
}
