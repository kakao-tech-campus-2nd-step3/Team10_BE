package poomasi.domain.farm.dto;

public record FarmScheduleRequest(
        Long farmId,
        Integer year,
        Integer month
) {
}

