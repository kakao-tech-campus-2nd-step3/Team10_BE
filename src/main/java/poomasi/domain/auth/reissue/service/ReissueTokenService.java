

package poomasi.domain.auth.reissue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.reissue.manager.RefreshTokenManager;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.auth.util.JwtUtil;
import poomasi.global.error.BusinessException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {
    /*
    private final JwtUtil jwtUtil;
    private final RefreshTokenManager refreshTokenManager;
    private final MemberRepository memberRepository;

    // 토큰 리프레시
    public TokenResponse refreshToken(final String refreshToken) {
        String email = jwtUtil.getEmailFromToken(refreshToken);

        Member member = getMemberByEmail(email);
        Long memberId = member.getId();

        checkRefreshToken(refreshToken, memberId);

        Map<String, Object> claims = createClaims(email, member.getRole());

        return getTokenResponse(memberId, claims);
    }

    public TokenResponse getTokenResponse(Long memberId, Map<String, Object> claims) {
        String newAccessToken = jwtUtil.generateAccessToken(String.valueOf(memberId), claims);
        refreshTokenManager.removeMemberRefreshToken(memberId);

        String newRefreshToken = jwtUtil.generateRefreshToken(String.valueOf(memberId), claims);
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

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public Map<String, Object> createClaims(String email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);
        return claims;
    }*/

}



/*
* import poomasi.global.error.BusinessException;

public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

* */

