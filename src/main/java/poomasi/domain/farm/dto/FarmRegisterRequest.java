package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;

public record FarmRegisterRequest(
        String name,
        Long ownerId,
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
                .ownerId(ownerId)
                .address(address)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .build();
    }
}
