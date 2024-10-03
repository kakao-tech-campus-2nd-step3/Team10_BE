package poomasi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.dto.request.SignUpRequest;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessException;

import static poomasi.domain.member.entity.Role.ROLE_CUSTOMER;
import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 카카오 로그인 일단 계정 분리 및 계정 연동 보류

    // 사업자 등록 번호 검사 로직은 추후 논의 필요

    @Transactional
    public void signUp(SignUpRequest signUpRequest, LoginType loginType) {
        // 이메일 중복 되어도 로그인 타입 다르면 중복 x
        if (memberRepository.findByEmailAndLoginType(signUpRequest.email(), loginType).isPresent()) {
            throw new BusinessException(DUPLICATE_MEMBER_EMAIL);
        }

        Member newMember = new Member(signUpRequest.email(), passwordEncoder.encode(signUpRequest.password()), loginType, ROLE_CUSTOMER);
        memberRepository.save(newMember);
    }

}
