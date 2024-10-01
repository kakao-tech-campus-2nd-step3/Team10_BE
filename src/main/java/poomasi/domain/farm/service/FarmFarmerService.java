package poomasi.domain.farm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.dto.FarmUpdateRequest;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessException;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
public class FarmFarmerService {
    private final FarmRepository farmRepository;

    public Long registerFarm(FarmRegisterRequest request) {
        // TODO: 판매자 인가?

        farmRepository.getFarmByOwnerIdAndDeletedAtIsNull(request.userId()).ifPresent(farm -> {
            System.out.println("이미 존재~");
            throw new BusinessException(FARM_ALREADY_EXISTS);
        });

        return farmRepository.save(request.toEntity()).getId();

    }

    public Long updateFarm(Long farmerId, FarmUpdateRequest request) {
        Farm farm = this.getFarmByFarmId(request.farmId());
        if (!farm.getOwnerId().equals(farmerId)) {
            throw new BusinessException(FARM_OWNER_MISMATCH);
        }

        // TODO: 변경 가능한 상태인가?

        return farmRepository.save(request.toEntity(farm)).getId();
    }

    public Farm getFarmByFarmId(Long farmId) {
        return farmRepository.findById(farmId).orElseThrow(() -> new BusinessException(FARM_NOT_FOUND));
    }

}
