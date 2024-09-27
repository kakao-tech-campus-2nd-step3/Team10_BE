package poomasi.domain.farm.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("")
    public ResponseEntity<?> getFarmList(Pageable pageable) {
        return ResponseEntity.ok(farmService.getFarmList(pageable));
    }
}
