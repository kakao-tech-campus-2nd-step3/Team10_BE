package poomasi.domain.farm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.farm.service.FarmPlatformService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmController {
    private final FarmPlatformService farmPlatformService;

    @GetMapping("/{farmId}")
    public ResponseEntity<?> getFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(farmPlatformService.getFarmByFarmId(farmId));
    }

    @GetMapping("")
    public ResponseEntity<?> getFarmList(Pageable pageable) {
        return ResponseEntity.ok(farmPlatformService.getFarmList(pageable));
    }
}
