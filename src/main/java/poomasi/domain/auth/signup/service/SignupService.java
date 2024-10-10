package poomasi.domain.auth.signup.service;

import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.signup.dto.request.SignupRequest;
import poomasi.domain.auth.signup.dto.response.SignUpResponse;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessException;

import static poomasi.domain.member.entity.Role.ROLE_CUSTOMER;
import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Description("카카오톡으로 먼저 회원가입이 되어 있는 경우, 계정 연동을 진행합니다. ")
    @Transactional
    public SignUpResponse signUp(SignupRequest signupRequest) {
        String email = signupRequest.email();
        String password = signupRequest.password();

        memberRepository.findByEmail(email)
                .ifPresent(member -> { throw new BusinessException(DUPLICATE_MEMBER_EMAIL); });

        Member newMember = new Member(email,
                passwordEncoder.encode(password),
                LoginType.LOCAL,
                ROLE_CUSTOMER);

        memberRepository.save(newMember);
        return new SignUpResponse(email, "회원 가입 성공");
    }
}

