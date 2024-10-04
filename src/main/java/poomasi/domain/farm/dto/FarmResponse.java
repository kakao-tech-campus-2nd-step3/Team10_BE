package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;


public record FarmResponse( // FIXME: 사용자 정보 추가 및 설명/전화번호 추가
                            Long id,
                            String name,
                            String address,
                            String addressDetail,
                            Double latitude,
                            Double longitude,
                            String description,
                            Long experiencePrice
) {
    public static FarmResponse fromEntity(Farm farm) {
        return new FarmResponse(
                farm.getId(),
                farm.getName(),
                farm.getAddress(),
                farm.getAddressDetail(),
                farm.getLatitude(),
                farm.getLongitude(),
                farm.getDescription(),
                farm.getExperiencePrice()
        );
    }
}
