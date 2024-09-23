package poomasi.domain.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.service.FarmManageService;

// TODO: 판매자만 접근가능하도록 인증/인가 annotation 추가
@RestController
@AllArgsConstructor
public class FarmMangeController {
    private final FarmManageService farmManageService;

    @PostMapping("/api/farm")
    public Long registerFarm(FarmRegisterRequest request) {
        return farmManageService.registerFarm(request);
    }
}
