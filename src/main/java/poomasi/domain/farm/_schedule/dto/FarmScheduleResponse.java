package poomasi.domain.farm._schedule.dto;

import lombok.Builder;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm._schedule.entity.ScheduleStatus;

import java.time.LocalDate;

@Builder
public record FarmScheduleResponse(
        LocalDate date,
        ScheduleStatus status
) {
    public static FarmScheduleResponse fromEntity(FarmSchedule farmSchedule) {
        return FarmScheduleResponse.builder()
                .date(farmSchedule.getDate())
                .status(farmSchedule.getStatus())
                .build();
    }
}
