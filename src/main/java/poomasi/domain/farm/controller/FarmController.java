package poomasi.domain.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.farm.service.FarmService;


@RestController
@AllArgsConstructor
@RequestMapping("/api/farm")
public class FarmController {
    private final FarmService farmService;

    @GetMapping("/{farmId}")
    public ResponseEntity<?> getFarm(@RequestParam Long farmId) {
        return ResponseEntity.ok(farmService.getFarmByFarmId(farmId));
    }

    // 농장 조회 페이지네이션
    @GetMapping("")
    public ResponseEntity<?> getFarmList(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(farmService.getFarmList(page, size));
    }
}
