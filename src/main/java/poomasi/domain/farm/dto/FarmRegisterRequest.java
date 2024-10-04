package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;

public record FarmRegisterRequest(
        String name,
        Long memberId,
        String address,
        String addressDetail,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String description,
        Long experiencePrice
) {
    public Farm toEntity() {
        return Farm.builder()
                .name(name)
                .ownerId(memberId)
                .address(address)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .experiencePrice(experiencePrice)
                .build();
    }
}
