package poomasi.domain.member._profile.dto.response;

import poomasi.domain.image.entity.Image;
import poomasi.domain.member._profile.entity.MemberProfile;

import java.time.LocalDateTime;

public record FarmerProfileResponse(
        String phoneNumber,
        boolean isBanned,
        LocalDateTime createdAt,
        Image profileImage
){
    public static FarmerProfileResponse fromEntity(MemberProfile profile) {
        return new FarmerProfileResponse(
                profile.getPhoneNumber(),
                profile.isBanned(),
                profile.getCreatedAt(),
                profile.getProfileImage()
        );
    }
}