package poomasi.domain.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.service.FarmSellerService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/farm/seller")
public class FarmSellerController {
    private final FarmSellerService farmSellerService;

    // TODO: 판매자만 접근가능하도록 인증/인가 annotation 추가
    @PostMapping("")
    public ResponseEntity<?> registerFarm(@RequestBody FarmRegisterRequest request) {
        return ResponseEntity.ok(farmSellerService.registerFarm(request));
    }
}
