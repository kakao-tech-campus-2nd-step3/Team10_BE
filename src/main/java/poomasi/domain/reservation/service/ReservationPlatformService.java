package poomasi.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.farm._schedule.service.FarmScheduleService;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.service.FarmService;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.reservation.dto.request.ReservationRequest;
import poomasi.domain.reservation.dto.response.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationPlatformService {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final FarmService farmService;
    private final FarmScheduleService farmScheduleService;

    public ReservationResponse createReservation(ReservationRequest request) {
        // 유효한 멤버를 가져온다.
        Member member = memberService.findMemberById(request.memberId());

        // 유효한 농장을 가져온다.
        Farm farm = farmService.getFarmByFarmId(request.farmId());

        // request가 가능한 날짜인가?

        // 예약을 생성한다.

        return null;
    }
}
