package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;

public record FarmRegisterRequest(
        String name,
        String address,
        String addressDetail,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String description,
        int experiencePrice,
        Integer maxCapacity,
        Integer maxReservation,
        String businessNumber
) {
    public Farm toEntity(Long memberId) {
        return Farm.builder()
                .name(name)
                .ownerId(memberId)
                .address(address)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .experiencePrice(experiencePrice)
                .maxCapacity(maxCapacity)
                .maxReservation(maxReservation)
                .businessNumber(businessNumber)
                .build();
    }
}
