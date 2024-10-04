package poomasi.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.global.error.BusinessException;

import static poomasi.domain.member.entity.Role.ROLE_FARMER;
import static poomasi.global.error.BusinessError.INVALID_FARMER_QUALIFICATION;
import static poomasi.global.error.BusinessError.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void upgradeToFarmer(Long memberId, Boolean hasFarmerQualification) {
        Member member = findMemberById(memberId);

        if (!hasFarmerQualification) {
            throw new BusinessException(INVALID_FARMER_QUALIFICATION);
        }

        member.setRole(ROLE_FARMER);
        memberRepository.save(member);
    }

    public boolean isFarmer(Long memberId) {
        Member member = findMemberById(memberId);
        return member.isFarmer();
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }
}
