package poomasi.domain.farm.dto;

import lombok.Builder;
import poomasi.domain.farm.entity.ScheduleStatus;

import java.time.LocalDate;

@Builder
public record FarmScheduleResponse(
        Long id,
        LocalDate date,
        ScheduleStatus status
) {
}
