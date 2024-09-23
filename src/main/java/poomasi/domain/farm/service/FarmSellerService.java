package poomasi.domain.farm.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.repository.FarmRepository;

@Service
@AllArgsConstructor
public class FarmSellerService {
    private final FarmRepository farmRepository;

    public Long registerFarm(FarmRegisterRequest request) {
        // TODO: 판매자 인가?

        // TODO: 이미 등록된 농장 인가?

        return farmRepository.save(request.toEntity()).getId();

    }
}
