package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;

public record FarmRegisterRequest(
        String name,
        Long userId,
        String address,
        String addressDetail,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String description
) {
    public Farm toEntity() {
        return Farm.builder()
                .name(name)
                .ownerId(userId)
                .address(address)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .build();
    }
}
