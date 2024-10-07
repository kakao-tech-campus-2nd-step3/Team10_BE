package poomasi.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.member.entity.Member;

import java.time.LocalDate;

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
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Comment("예약자")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Comment("예약 시간")
    @Column(name = "reservation_time")
    @OneToOne(fetch = FetchType.LAZY)
    private FarmSchedule scheduleId;

    @Comment("예약 날짜")
    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Comment("예약 인원")
    @Column(name = "reservation_count")
    private int reservationCount;

    @Comment("예약 상태")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Comment("요청 사항")
    @Column(name = "request")
    private String request;


    @Builder
    public Reservation(Farm farm, Member member, FarmSchedule scheduleId, LocalDate reservationDate, int reservationCount, ReservationStatus status, String request) {
        this.farm = farm;
        this.member = member;
        this.scheduleId = scheduleId;
        this.reservationDate = reservationDate;
        this.reservationCount = reservationCount;
        this.status = status;
        this.request = request;
    }
}
