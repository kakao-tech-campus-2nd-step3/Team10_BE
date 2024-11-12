package poomasi.domain.image.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poomasi.domain.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberProfileOwnerValidator implements ImageOwnerValidator{
    private final MemberRepository memberRepository;

    @Override
    public boolean validateOwner(Long memberId, Long referenceId) {
        return memberRepository.findById(memberId)
                .filter(member -> member.getMemberProfile().getId().equals(referenceId))
                .isPresent();
    }
}
