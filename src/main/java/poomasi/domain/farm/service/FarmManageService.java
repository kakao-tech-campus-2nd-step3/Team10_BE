package poomasi.domain.farm.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.dto.FarmResponse;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@AllArgsConstructor
public class FarmManageService {
    private final FarmRepository farmRepository;

    public Long registerFarm(FarmRegisterRequest request) {
        // TODO: 판매자 인가?

        // TODO: 이미 등록된 농장 인가?


        return farmRepository.save(request.toEntity()).getId();
    }

    public FarmResponse getFarmByFarmId(Long farmId) {
        return farmRepository.findById(farmId)
                .map(FarmResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
    }


}
