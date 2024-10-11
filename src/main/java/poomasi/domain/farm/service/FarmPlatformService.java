package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmPlatformService {
    private final FarmService farmService;

    public FarmResponse getFarmByFarmId(Long farmId) {
        return FarmResponse.fromEntity(farmService.getFarmByFarmId(farmId));
    }

    public List<FarmResponse> getFarmList(Pageable pageable) {
        return farmService.getFarmList(pageable).stream()
                .map(FarmResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
