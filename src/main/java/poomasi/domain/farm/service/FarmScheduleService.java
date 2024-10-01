package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmScheduleRequest;
import poomasi.domain.farm.dto.FarmScheduleResponse;
import poomasi.domain.farm.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm.entity.FarmSchedule;
import poomasi.domain.farm.repository.FarmScheduleRepository;
import poomasi.global.error.BusinessException;

import java.time.LocalDate;
import java.util.List;

import static poomasi.global.error.BusinessError.FARM_SCHEDULE_ALREADY_EXISTS;
import static poomasi.global.error.BusinessError.FARM_SCHEDULE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FarmScheduleService {
    private final FarmScheduleRepository farmScheduleRepository;

    public void addFarmSchedule(FarmScheduleUpdateRequest request) {
        for (LocalDate date = request.startDate(); !date.isAfter(request.endDate()); date = date.plusDays(1)) {
            if (request.availableDays().contains(date.getDayOfWeek())) {
                farmScheduleRepository.findByFarmIdAndDate(request.farmId(), date)
                        .ifPresent(schedule -> {
                            throw new BusinessException(FARM_SCHEDULE_ALREADY_EXISTS);
                        });

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
