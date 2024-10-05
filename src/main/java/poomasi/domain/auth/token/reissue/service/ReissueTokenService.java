package poomasi.domain.auth.token.reissue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.token.dto.response.TokenResponse;
import poomasi.domain.auth.token.reissue.dto.ReissueRequest;
import poomasi.domain.auth.token.refreshtoken.service.RefreshTokenService;
import poomasi.global.error.BusinessException;
import poomasi.domain.auth.token.util.JwtUtil;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    // 토큰 재발급
    public TokenResponse reissueToken(ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        Long memberId = jwtUtil.getIdFromToken(refreshToken);

        checkRefreshToken(refreshToken, memberId);

        return getTokenResponse(memberId);
    }

    public TokenResponse getTokenResponse(Long memberId) {
        String newAccessToken = jwtUtil.generateAccessTokenById(memberId);
        refreshTokenService.removeMemberRefreshToken(memberId);

        String newRefreshToken = jwtUtil.generateRefreshTokenById(memberId);
        refreshTokenService.putRefreshToken(newRefreshToken, memberId);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private void checkRefreshToken(final String refreshToken, Long memberId) {
        if(!jwtUtil.validateRefreshToken(refreshToken, memberId))
            throw new BusinessException(REFRESH_TOKEN_NOT_VALID);
    }
}
