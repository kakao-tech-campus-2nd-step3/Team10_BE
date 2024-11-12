package poomasi.domain.member.service;

import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member._profile.dto.request.AddressUpdateRequest;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.member.dto.request.CustomerUpdateRequest;
import poomasi.domain.member.dto.request.FarmerUpdateRequest;
import poomasi.domain.member.dto.response.MemberResponse;
import poomasi.domain.member.dto.response.MemberSummaryResponse;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.domain.member.dto.request.SignupRequest;
import poomasi.domain.member.dto.response.SignUpResponse;
import poomasi.domain.store.entity.Store;
import poomasi.global.error.BusinessException;

import java.util.Optional;

import static poomasi.domain.member.entity.Role.ROLE_CUSTOMER;
import static poomasi.domain.member.entity.Role.ROLE_FARMER;
import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Description("카카오톡으로 먼저 회원가입이 되어 있는 경우, 계정 연동을 진행합니다. ")
    @Transactional
    public SignUpResponse signUp(SignupRequest signupRequest) {
        String name = signupRequest.name();
        String email = signupRequest.email();
        String password = signupRequest.password();

        memberRepository.findByEmailAndDeletedAtIsNull(email)
                .ifPresent(member -> { throw new BusinessException(DUPLICATE_MEMBER_EMAIL); });

        Member newMember = new Member(name, email,
                passwordEncoder.encode(password),
                LoginType.LOCAL,
                ROLE_CUSTOMER);

        memberRepository.save(newMember);
        return new SignUpResponse(name, email, "회원 가입 성공");
    }

    public MemberResponse getMemberById(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberResponse.fromEntity(member);
    }

    public MemberSummaryResponse getMemberSummary(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberSummaryResponse.fromEntity(member);
    }

    public Page<MemberSummaryResponse> getAllMembersSummary(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberSummaryResponse::fromEntity);
    }

    @Transactional
    public void convertToFarmer(Member member) {
        if (member.isFarmer()) {
            throw new BusinessException(MEMBER_ALREADY_FARMER);
        }

        member.setRole(ROLE_FARMER);
        memberRepository.save(member);
    }

    @Transactional
    public void convertToCustomer(Long memberId) {
        Member member = findMemberById(memberId);

        if (member.isCustomer()) {
            throw new BusinessException(MEMBER_ALREADY_CUSTOMER);
        }

        member.setRole(ROLE_CUSTOMER);
        memberRepository.save(member);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member updateCustomer(Member member, CustomerUpdateRequest customerUpdateRequest)
    {
        if (!member.isCustomer()) {
            throw new BusinessException(INVALID_ROLE);
        }

        updateCommonAttributes(member, customerUpdateRequest.name(),customerUpdateRequest.email(), customerUpdateRequest.password(), customerUpdateRequest.phoneNumber());

        return memberRepository.save(member);
    }

    @Transactional
    public Member updateFarmer(Member member, FarmerUpdateRequest farmerUpdateRequest)
    {
        if (!member.isFarmer()) {
            throw new BusinessException(INVALID_ROLE);
        }

        updateCommonAttributes(member, farmerUpdateRequest.name(), farmerUpdateRequest.email(), farmerUpdateRequest.password(), farmerUpdateRequest.phoneNumber());

        Store store = member.getOrCreateStore();

        if (farmerUpdateRequest.storeName() != null) {
            store.setName(farmerUpdateRequest.storeName());
        }
        if (farmerUpdateRequest.storeAddress() != null) {
            store.setAddress(farmerUpdateRequest.storeAddress());
        }

        return memberRepository.save(member);
    }

    private void updateCommonAttributes(Member member, String name, String email, String password, String phoneNumber) {
        if (name != null) member.setName(name);
        if (email != null) member.setEmail(email);
        if (password != null) member.setPassword(passwordEncoder.encode(password));

        MemberProfile profile = member.getOrCreateProfile();
        if (phoneNumber != null) {
            profile.setPhoneNumber(phoneNumber);
        }
    }

    @Transactional
    public void updateAddress(Member member, AddressUpdateRequest request) {
        MemberProfile profile = member.getOrCreateProfile();

        profile.setAddress(request.defaultAddress(), request.addressDetail(), request.coordinateX(), request.coordinateY());

        memberRepository.save(member);
    }

}
