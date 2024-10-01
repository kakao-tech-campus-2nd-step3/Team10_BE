package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.entity.FarmSchedule;
import poomasi.domain.farm.entity.ScheduleStatus;

import java.time.LocalDate;

public record FarmScheduleUpdateRequest(
        Long farmId,
        LocalDate date
) {
    public FarmSchedule toEntity(Farm farm) {
        return FarmSchedule.builder()
                .farm(farm)
                .date(date)
                .status(ScheduleStatus.PENDING)
                .build();
    }
}
