package poomasi.domain.farm.dto;

import jakarta.validation.constraints.NotNull;
import poomasi.domain.farm.entity.Farm;

public record FarmUpdateRequest(
        @NotNull(message = "Farm ID는 필수 값입니다.") Long farmId,
        String name,
        String description,
        String address,
        String addressDetail,
        Double latitude,
        Double longitude
) {
    public Farm toEntity(Farm farm) {
        return farm.updateFarm(this);
    }
}
