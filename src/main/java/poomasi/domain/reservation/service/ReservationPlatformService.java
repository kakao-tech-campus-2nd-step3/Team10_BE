package poomasi.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm._schedule.service.FarmScheduleService;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.service.FarmService;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.reservation.dto.request.ReservationRequest;
import poomasi.domain.reservation.dto.response.ReservationResponse;
import poomasi.domain.reservation.entity.Reservation;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ReservationPlatformService {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final FarmService farmService;
    private final FarmScheduleService farmScheduleService;

    public ReservationResponse createReservation(ReservationRequest request) {
        Member member = memberService.findMemberById(request.memberId());
        Farm farm = farmService.getValidFarmByFarmId(request.farmId());
        FarmSchedule farmSchedule = farmScheduleService.getValidFarmScheduleByFarmIdAndDate(request.farmId(), request.reservationDate());

        // TODO: 예약 가능한지 확인하는 로직 추가

        Reservation reservation = reservationService.createReservation(request.toEntity(member, farm, farmSchedule));


        return reservation.toResponse();
    }

    public ReservationResponse getReservation(Long memberId, Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (!reservation.getMember().getId().equals(memberId) || memberService.isAdmin(memberId)) {
            throw new BusinessException(BusinessError.RESERVATION_NOT_ACCESSIBLE);
        }

        return reservation.toResponse();
    }
}
