package poomasi.domain.member._profile.dto.response;

import poomasi.domain.image.entity.Image;
import poomasi.domain.member._profile.entity.MemberProfile;

import java.time.LocalDateTime;

public record MemberProfileResponse(
        String phoneNumber,
        String defaultAddress,
        String addressDetail,
        Long coordinateX,
        Long coordinateY,
        boolean isBanned,
        LocalDateTime createdAt,
        Image profileImage){


    public static MemberProfileResponse fromEntity(MemberProfile profile) {
        return new MemberProfileResponse(
                profile.getPhoneNumber(),
                profile.getDefaultAddress(),
                profile.getAddressDetail(),
                profile.getCoordinateX(),
                profile.getCoordinateY(),
                profile.isBanned(),
                profile.getCreatedAt(),
                profile.getProfileImage()
        );
    }
}