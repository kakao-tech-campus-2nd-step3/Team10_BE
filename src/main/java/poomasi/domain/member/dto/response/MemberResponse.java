package poomasi.domain.member.dto.response;

import poomasi.domain.member._profile.dto.response.MemberProfileResponse;
import poomasi.domain.member.entity.Member;

public record MemberResponse(
        Long id,
        String name,
        String email,
        String role,
        MemberProfileResponse memberProfile
) {
    public static MemberResponse fromEntity(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole().name(),
                member.getMemberProfile() != null ? MemberProfileResponse.fromEntity(member.getMemberProfile()) : null
        );
    }
}
