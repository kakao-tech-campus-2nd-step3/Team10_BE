package poomasi.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.reservation.service.ReservationFarmerService;

@RequestMapping("/api/v1/farmer/reservations")
@RestController
@RequiredArgsConstructor
public class ReservationFarmerController {
    private final ReservationFarmerService reservationFarmerService;

    @GetMapping("")
    public ResponseEntity<?> getReservations() {
        // FIXME : 임시로 farmerId를 1로 고정
        Long farmerId = 1L;
        return ResponseEntity.ok(reservationFarmerService.getReservationsByFarmerId(farmerId));
    }
}
