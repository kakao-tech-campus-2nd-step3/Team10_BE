package poomasi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.auth.entity.RefreshToken;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.global.error.BusinessException;
import poomasi.global.util.JwtProvider;

import java.util.HashMap;
import java.util.Map;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtProvider jwtProvider;
    private final RefreshToken refreshTokenManager;
    private final MemberRepository memberRepository;

    // 토큰 리프레시
    public TokenResponse refreshToken(final String refreshToken) {
        String email = jwtProvider.getSubjectFromToken(refreshToken);

        Long memberId = getMemberIdByEmail(email);

        checkRefreshToken(refreshToken, memberId);

        return getTokenResponse(memberId, email, jwtProvider, refreshTokenManager);
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


    private void checkRefreshToken(final String refreshToken, Long memberId) {
        if(!jwtProvider.validateRefreshToken(refreshToken, memberId))
            throw new BusinessException(REFRESH_TOKEN_NOT_VALID);
    }

    public Long getMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        return member.getId();
    }
}
