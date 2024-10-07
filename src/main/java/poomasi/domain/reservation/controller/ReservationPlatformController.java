package poomasi.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/get/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable Long reservationId) {
        // FIXME: 로그인한 사용자의 ID를 가져오도록 수정
        Long memberId = 1L;
        ReservationResponse reservation = reservationPlatformService.getReservation(memberId, reservationId);
        return ResponseEntity.ok(reservation);
    }

}
