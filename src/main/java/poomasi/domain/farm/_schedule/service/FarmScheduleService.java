package poomasi.domain.farm._schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm._schedule.dto.FarmScheduleRequest;
import poomasi.domain.farm._schedule.dto.FarmScheduleResponse;
import poomasi.domain.farm._schedule.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm._schedule.repository.FarmScheduleRepository;
import poomasi.global.error.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static poomasi.global.error.BusinessError.FARM_SCHEDULE_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class FarmScheduleService {
    private final FarmScheduleRepository farmScheduleRepository;

    public void addFarmSchedule(FarmScheduleUpdateRequest request) {
        List<FarmSchedule> existingSchedules = farmScheduleRepository.findByFarmIdAndDateRange(request.farmId(), request.startDate(), request.endDate());

        Set<LocalDate> existingDates = existingSchedules.stream()
                .map(FarmSchedule::getDate)
                .collect(Collectors.toSet());

        for (LocalDate date = request.startDate(); !date.isAfter(request.endDate()); date = date.plusDays(1)) {
            if (request.availableDays().contains(date.getDayOfWeek())) {
                if (existingDates.contains(date)) {
                    throw new BusinessException(FARM_SCHEDULE_ALREADY_EXISTS);
                }

                FarmSchedule newSchedule = request.toEntity(date);
                farmScheduleRepository.save(newSchedule);
            }
        }
    }

    public List<FarmScheduleResponse> getFarmSchedulesByYearAndMonth(FarmScheduleRequest request) {
        LocalDate startDate = LocalDate.of(request.year(), request.month(), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return farmScheduleRepository.findByFarmIdAndDateRange(request.farmId(), startDate, endDate).stream()
                .map(FarmScheduleResponse::fromEntity)
                .toList();
    }


}
