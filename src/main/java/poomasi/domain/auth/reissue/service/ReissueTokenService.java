package poomasi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.auth.entity.RefreshToken;
import poomasi.global.error.BusinessException;
import poomasi.domain.auth.util.JwtUtil;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshToken refreshTokenManager;

    // 토큰 리프레시
    public TokenResponse refreshToken(final String refreshToken) {
        Long memberId = jwtUtil.getIdFromToken(refreshToken);

        checkRefreshToken(refreshToken, memberId);

        return getTokenResponse(memberId);
    }

    public TokenResponse getTokenResponse(Long memberId) {
        String newAccessToken = jwtUtil.generateAccessTokenById(memberId);
        refreshTokenManager.removeMemberRefreshToken(memberId);

        String newRefreshToken = jwtUtil.generateRefreshTokenById(memberId);
        refreshTokenManager.putRefreshToken(newRefreshToken, memberId);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    public void removeRefreshTokenById(Long memberId) {
        refreshTokenManager.removeMemberRefreshToken(memberId);
    }

    private void checkRefreshToken(final String refreshToken, Long memberId) {
        if(!jwtUtil.validateRefreshToken(refreshToken, memberId))
            throw new BusinessException(REFRESH_TOKEN_NOT_VALID);
    }
}
