package poomasi.domain.farm.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmResponse;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@AllArgsConstructor
public class FarmService {
    private final FarmRepository farmRepository;

    public FarmResponse getFarmByFarmId(Long farmId) {
        return farmRepository.findById(farmId)
                .map(FarmResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
    }


}
