package poomasi.domain.auth.token.reissue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.token.reissue.dto.ReissueRequest;
import poomasi.domain.auth.token.refreshtoken.service.RefreshTokenService;
import poomasi.domain.auth.token.reissue.dto.ReissueResponse;
import poomasi.global.error.BusinessException;
import poomasi.domain.auth.token.util.JwtUtil;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    // 토큰 재발급
    public ReissueResponse reissueToken(ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();
        Long memberId = jwtUtil.getIdFromToken(refreshToken);

        checkRefreshToken(refreshToken, memberId);

        return getTokenResponse(memberId);
    }

    public ReissueResponse getTokenResponse(Long memberId) {
        String newAccessToken = jwtUtil.generateAccessTokenById(memberId);
        refreshTokenService.removeMemberRefreshToken(memberId);

        String newRefreshToken = jwtUtil.generateRefreshTokenById(memberId);
        refreshTokenService.putRefreshToken(newRefreshToken, memberId);

        return new ReissueResponse(newAccessToken, newRefreshToken);
    }

    private void checkRefreshToken(final String refreshToken, Long memberId) {
        if(!jwtUtil.validateRefreshToken(refreshToken, memberId))
            throw new BusinessException(REFRESH_TOKEN_NOT_VALID);
    }
}
