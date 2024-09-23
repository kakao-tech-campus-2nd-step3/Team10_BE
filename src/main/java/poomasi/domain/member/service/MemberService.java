package poomasi.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poomasi.domain.member.dto.request.LoginRequest;
import poomasi.domain.member.dto.response.LoginResponse;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import static poomasi.domain.member.entity.LoginType.LOCAL;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    public MemberService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndLoginType(loginRequest.email(), LOCAL)
                .orElseThrow(() -> new MemberNotFoundExeption(MEMBER_NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequest.password(), member.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIAL);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());

        String accessToken = jwtUtil.generateAccessToken(member.getEmail(), claims);
        String refreshToken = jwtUtil.generateRefreshToken(member.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }
}
