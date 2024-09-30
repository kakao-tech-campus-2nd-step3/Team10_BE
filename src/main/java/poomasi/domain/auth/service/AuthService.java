package poomasi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.auth.entity.RefreshToken;
import poomasi.domain.auth.dto.request.LoginRequest;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessException;
import poomasi.global.redis.service.RedisService;
import poomasi.global.util.JwtProvider;

import java.time.Duration;
import java.util.Optional;

import static poomasi.domain.auth.service.RefreshTokenService.getTokenResponse;
import static poomasi.domain.member.entity.Role.ROLE_CUSTOMER;
import static poomasi.domain.member.entity.Role.ROLE_FARMER;
import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RefreshToken refreshTokenManager;
    private final RedisService redisService;

    // 할거: 카카오 로그인
    // 카카오 로그인과 같은 이메일로 일반 회원가입 할 경우 계정 통합
    // 일반 회원가입 한 것과 같은 이메일로 카카오 로그인 할 경우 계정 통합
    // 통합시 로그인 타입은 LOCAL

    // 사업자 등록 번호 검사 로직은 추후 논의 필요

    @Transactional
    public TokenResponse signUp(LoginRequest loginRequest, LoginType loginType) {

        Optional<Member> existingMember = memberRepository.findByEmail(loginRequest.email());

        // 기존 로컬 계정이 있는 경우
        if (existingMember.isPresent()) {
            // 로그인 타입이 카카오인 경우, 계정 통합
            if (loginType != LoginType.LOCAL) {
                Member member = existingMember.get();
                member.kakaoToLocal(loginRequest.password());
                memberRepository.save(member);
                return getTokenResponse(member.getId(), member.getEmail(), jwtProvider, refreshTokenManager);
            } else {
                throw new BusinessException(DUPLICATE_MEMBER_EMAIL);
            }
        }

        Member newMember = new Member(loginRequest.email(), new BCryptPasswordEncoder().encode(loginRequest.password()), loginType, ROLE_CUSTOMER);
        memberRepository.save(newMember);
        return getTokenResponse(newMember.getId(), newMember.getEmail(), jwtProvider, refreshTokenManager);
    }

    @Transactional
    public void upgradeToFarmer(Long memberId, Boolean hasFarmerQualification) {
        Member member = findMemberById(memberId);

        if (!hasFarmerQualification) {
            throw new BusinessException(INVALID_FARMER_QUALIFICATION);
        }

        member.setRole(ROLE_FARMER);
        memberRepository.save(member);
    }

    @Transactional
    public void logout(Long memberId, String accessToken) {
        refreshTokenManager.removeMemberRefreshToken(memberId);

        redisService.setBlackList(accessToken, "accessToken", Duration.ofMinutes(5));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }


}
