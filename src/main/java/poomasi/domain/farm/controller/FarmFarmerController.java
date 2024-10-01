package poomasi.domain.farm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm.dto.FarmUpdateRequest;
import poomasi.domain.farm.service.FarmFarmerService;
import poomasi.domain.farm.service.FarmScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmFarmerController {
    private final FarmFarmerService farmFarmerService;
    private final FarmScheduleService farmScheduleService;

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

    @DeleteMapping("/{farmId}")
    public ResponseEntity<?> deleteFarm(@PathVariable Long farmId) {
        // TODO: 판매자 ID
        Long farmerId = 1L;

        farmFarmerService.deleteFarm(farmerId, farmId);
        return ResponseEntity.ok().build();
    }

}
