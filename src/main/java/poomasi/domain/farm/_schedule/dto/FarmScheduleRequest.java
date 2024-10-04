package poomasi.domain.farm._schedule.dto;

public record FarmScheduleRequest(
        Long farmId,
        Integer year,
        Integer month
) {
}

