package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmScheduleResponse;
import poomasi.domain.farm.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm.entity.Farm;
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
    private final FarmFarmerService farmFarmerService;

    public void addFarmSchedule(FarmScheduleUpdateRequest request) {
        Farm farm = farmFarmerService.getFarmByFarmId(request.farmId());

        for (LocalDate date = request.startDate(); !date.isAfter(request.endDate()); date = date.plusDays(1)) {
            if (request.availableDays().contains(date.getDayOfWeek())) {
                farmScheduleRepository.findByFarmIdAndDate(request.farmId(), date)
                        .ifPresent(schedule -> {
                            throw new BusinessException(FARM_SCHEDULE_ALREADY_EXISTS);
                        });

                FarmSchedule newSchedule = request.toEntity(farm, date);
                farmScheduleRepository.save(newSchedule);
            }
        }
    }

    public void updateFarmSchedule(FarmScheduleUpdateRequest request) {
        for (LocalDate date = request.startDate(); !date.isAfter(request.endDate()); date = date.plusDays(1)) {
            if (request.availableDays().contains(date.getDayOfWeek())) {
                FarmSchedule schedule = farmScheduleRepository.findByFarmIdAndDate(request.farmId(), date)
                        .orElseThrow(() -> new BusinessException(FARM_SCHEDULE_NOT_FOUND));

                schedule.updateStatus(request.status());
                farmScheduleRepository.save(schedule);
            }
        }
    }

    public List<FarmScheduleResponse> getFarmSchedule(Long farmId, Long month) {
        return farmScheduleRepository.findByFarmIdAndMonth(farmId, month).stream()
                .map(schedule -> new FarmScheduleResponse(schedule.getId(), schedule.getDate(), schedule.getStatus()))
                .toList();
    }

    public FarmSchedule getFarmSchedule(Long farmId, LocalDate date) {
        return farmScheduleRepository.findByFarmIdAndDate(farmId, date)
                .orElseThrow(() -> new BusinessException(FARM_SCHEDULE_NOT_FOUND));
    }
}
