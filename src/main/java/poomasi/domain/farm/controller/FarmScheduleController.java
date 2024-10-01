package poomasi.domain.farm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
