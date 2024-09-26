package poomasi.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.dto.request.LoginRequest;
import poomasi.domain.member.dto.response.LoginResponse;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessException;
import poomasi.global.util.JwtProvider;

import java.util.HashMap;
import java.util.Map;

import static poomasi.domain.member.entity.LoginType.LOCAL;
import static poomasi.global.error.BusinessError.INVALID_CREDENTIAL;
import static poomasi.global.error.BusinessError.MEMBER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    public MemberService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // 할거: 리프레시 토큰 적용, 로그아웃, 회원가입, Redis 연동

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndLoginType(loginRequest.email(), LOCAL)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequest.password(), member.getPassword())) {
            throw new BusinessException(INVALID_CREDENTIAL);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());

        String accessToken = jwtProvider.generateAccessToken(member.getEmail(), claims);
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }
}
