package poomasi.domain.farm._schedule.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm._schedule.entity.ScheduleStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record FarmScheduleUpdateRequest(
        Long farmId,
        @NotNull(message = "시작 날짜는 필수 값입니다.")
        LocalDate startDate,
        @NotNull(message = "종료 날짜는 필수 값입니다.")
        LocalDate endDate,
        ScheduleStatus status,
        @NotEmpty(message = "예약 가능한 요일은 필수 값입니다.")
        List<DayOfWeek> availableDays // 예약 가능한 요일 리스트
) {
    public FarmSchedule toEntity(LocalDate date) {
        return FarmSchedule.builder()
                .farmId(farmId)
                .date(date)
                .status(status)
                .build();
    }
}
