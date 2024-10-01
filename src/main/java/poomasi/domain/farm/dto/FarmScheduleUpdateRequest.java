package poomasi.domain.farm.dto;

import poomasi.domain.farm.entity.FarmSchedule;
import poomasi.domain.farm.entity.ScheduleStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record FarmScheduleUpdateRequest(
        Long farmId,
        LocalDate startDate, // 시작 날짜
        LocalDate endDate,   // 종료 날짜
        ScheduleStatus status, // 예약 가능 여부
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
