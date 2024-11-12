package poomasi.domain.member.dto.response;

import poomasi.domain.member._profile.dto.response.FarmerProfileResponse;
import poomasi.domain.member.entity.Member;

public record FarmerResponse(
        Long id,
        String name,
        String email,
        String role,
        FarmerProfileResponse memberProfile
) {
    public static FarmerResponse fromEntity(Member member) {
        return new FarmerResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole().name(),
                member.getMemberProfile() != null ? FarmerProfileResponse.fromEntity(member.getMemberProfile()) : null
        );
    }
}