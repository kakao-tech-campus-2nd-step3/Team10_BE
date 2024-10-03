package poomasi.domain.farm.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.dto.FarmUpdateRequest;
import poomasi.domain.farm.service.FarmFarmerService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/farm")
public class FarmFarmerController {

    private final FarmFarmerService farmFarmerService;

    // TODO: 판매자만 접근가능하도록 인증/인가 annotation 추가
    @PostMapping("")
    public ResponseEntity<?> registerFarm(@RequestBody FarmRegisterRequest request) {
        return ResponseEntity.ok(farmFarmerService.registerFarm(request));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateFarm(@Valid @RequestBody FarmUpdateRequest request) {
        // TODO: 판매자 ID(Spring Security Context)로 대체
        Long farmerId = 1L;
        return ResponseEntity.ok(farmFarmerService.updateFarm(farmerId, request));
    }
}
