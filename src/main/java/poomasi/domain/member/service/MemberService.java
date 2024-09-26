package poomasi.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.auth.entity.RefreshToken;
import poomasi.domain.member.dto.request.LoginRequest;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessException;
import poomasi.global.util.JwtProvider;

import static poomasi.domain.auth.service.RefreshTokenService.getTokenResponse;
import static poomasi.domain.member.entity.LoginType.LOCAL;
import static poomasi.global.error.BusinessError.INVALID_CREDENTIAL;
import static poomasi.global.error.BusinessError.MEMBER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    private RefreshToken refreshTokenManager;

    public MemberService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // 할거: 로그아웃, 일반 회원가입, 농부로 회원가입, 카카오 로그인
    // 카카오 로그인과 같은 이메일로 일반 회원가입 할 경우 계정 통합

    public TokenResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndLoginType(loginRequest.email(), LOCAL)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequest.password(), member.getPassword())) {
            throw new BusinessException(INVALID_CREDENTIAL);
        }

        Long memberId = member.getId();
        String memberEmail = member.getEmail();

        return getTokenResponse(memberId, memberEmail, jwtProvider, refreshTokenManager);
    }

}
