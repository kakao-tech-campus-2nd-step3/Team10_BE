package poomasi.domain.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.service.FarmManageService;


@RestController
@AllArgsConstructor
public class FarmMangeController {
    private final FarmManageService farmManageService;

    @GetMapping("/api/farm/{farmId}")
    public ResponseEntity<?> getFarm(@RequestParam Long farmId) {
        return ResponseEntity.ok(farmManageService.getFarmByFarmId(farmId));

    }

    // TODO: 판매자만 접근가능하도록 인증/인가 annotation 추가
    @PostMapping("/api/farm")
    public ResponseEntity<?> registerFarm(@RequestBody FarmRegisterRequest request) {
        return ResponseEntity.ok(farmManageService.registerFarm(request));
    }


}
