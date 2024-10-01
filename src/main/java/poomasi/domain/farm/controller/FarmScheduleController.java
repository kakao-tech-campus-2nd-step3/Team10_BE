package poomasi.domain.farm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.farm.dto.FarmScheduleRequest;
import poomasi.domain.farm.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm.service.FarmScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmScheduleController {
    private final FarmScheduleService farmScheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<?> addFarmSchedule(@RequestBody FarmScheduleUpdateRequest request) {
        farmScheduleService.addFarmSchedule(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> getFarmSchedule(@RequestParam Long farmId, @RequestParam Integer year, @RequestParam Integer month) {
        return ResponseEntity.ok(farmScheduleService.getFarmSchedulesByYearAndMonth(new FarmScheduleRequest(farmId, year, month)));
    }
}
