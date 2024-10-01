package poomasi.domain.farm._schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.farm._schedule.dto.FarmScheduleRequest;
import poomasi.domain.farm._schedule.service.FarmScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmScheduleController {
    private final FarmScheduleService farmScheduleService;

    @GetMapping("/schedule")
    public ResponseEntity<?> getFarmSchedule(@RequestParam Long farmId, @RequestParam Integer year, @RequestParam Integer month) {
        return ResponseEntity.ok(farmScheduleService.getFarmSchedulesByYearAndMonth(new FarmScheduleRequest(farmId, year, month)));
    }
}
