package poomasi.domain.member._profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.member._profile.repository.MemberProfileRepository;
import poomasi.global.error.BusinessException;

import static poomasi.global.error.BusinessError.MEMBER_PROFILE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;

    public MemberProfile getMemberProfileById(Long id){
        return memberProfileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_PROFILE_NOT_FOUND));
    }

    @Transactional
    public MemberProfile saveMemberProfile(MemberProfile memberProfile){
        return memberProfileRepository.save(memberProfile);
    }
}
