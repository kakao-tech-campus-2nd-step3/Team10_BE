package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    private final FarmFarmerService farmFarmerService;

    public void addFarmSchedule(FarmScheduleUpdateRequest request) {
        // TODO: ADMIN인가, 자신의 FARM인가 확인

        farmScheduleRepository.findByFarmIdAndDate(request.farmId(), request.date())
                .ifPresent(schedule -> {
                    throw new BusinessException(FARM_SCHEDULE_ALREADY_EXISTS);
                });

        farmScheduleRepository.save(request.toEntity(farmFarmerService.getFarmByFarmId(request.farmId())));
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
