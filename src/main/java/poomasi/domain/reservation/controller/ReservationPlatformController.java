package poomasi.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.reservation.dto.request.ReservationRequest;
import poomasi.domain.reservation.dto.response.ReservationResponse;
import poomasi.domain.reservation.service.ReservationPlatformService;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationPlatformController {
    private final ReservationPlatformService reservationPlatformService;

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse reservation = reservationPlatformService.createReservation(request);
        return ResponseEntity.ok(reservation);
    }


}
