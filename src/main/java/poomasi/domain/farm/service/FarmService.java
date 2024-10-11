package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmResponse;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.entity.FarmStatus;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmService {
    private final FarmRepository farmRepository;

    public Farm getValidFarmByFarmId(Long farmId) {
        Farm farm = getFarmByFarmId(farmId);
        if (farm.getStatus() != FarmStatus.OPEN) {
            throw new BusinessException(BusinessError.FARM_NOT_OPEN);
        }
        return farm;
    }

    public Farm getFarmByFarmId(Long farmId) {
        return farmRepository.findByIdAndDeletedAtIsNull(farmId)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
    }

    public List<Farm> getFarmList(Pageable pageable) {
        return farmRepository.findByDeletedAtIsNull(pageable).stream()
                .collect(Collectors.toList());
    }
}
