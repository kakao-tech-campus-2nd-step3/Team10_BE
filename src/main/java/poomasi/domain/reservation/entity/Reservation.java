package poomasi.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.member.entity.Member;
import poomasi.domain.reservation.dto.response.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "reservation", indexes = {
        @Index(name = "idx_farm_id", columnList = "farm_id"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("농장")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Comment("예약자")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Comment("예약 시간")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private FarmSchedule scheduleId;

    @Comment("예약 날짜")
    @Column(nullable = false)
    private LocalDate reservationDate;

    @Comment("예약 인원")
    @Column(nullable = false)
    private int memberCount;

    @Comment("예약 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Comment("요청 사항")
    @Column(nullable = false)
    private String request;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Comment("예약 취소 일자")
    private LocalDateTime canceledAt;


    @Builder
    public Reservation(Farm farm, Member member, FarmSchedule scheduleId, LocalDate reservationDate, int memberCount, ReservationStatus status, String request) {
        this.farm = farm;
        this.member = member;
        this.scheduleId = scheduleId;
        this.reservationDate = reservationDate;
        this.memberCount = memberCount;
        this.status = status;
        this.request = request;
    }

    public ReservationResponse toResponse() {
        return ReservationResponse.builder()
                .farmId(farm.getId())
                .memberId(member.getId())
                .scheduleId(scheduleId.getId())
                .reservationDate(reservationDate)
                .memberCount(memberCount)
                .status(status)
                .request(request)
                .build();
    }

    public boolean isCanceled() {
        return status == ReservationStatus.CANCELED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
    }
}
