package poomasi.domain.farm.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmResponse;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmResponse getFarmByFarmId(Long farmId) {
        return farmRepository.findById(farmId)
                .map(FarmResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
    }


    public List<FarmResponse> getFarmList(Pageable pageable) {
        return farmRepository.findAll(pageable).stream()
                .map(FarmResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
