package poomasi.domain.farm.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.farm.dto.FarmUpdateRequest;

import java.time.LocalDateTime;

import poomasi.domain.order.entity._farm.OrderedFarm;
import poomasi.domain.review.entity.Review;

@Entity
@Getter
@Table(name = "farm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE farm SET deleted_at=current_timestamp WHERE id = ?")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Comment("농장 소유자 ID")
    @Column(name = "owner_id")
    private Long ownerId;

    @Comment("사업자 등록 번호")
    private String businessNumber;

    @Comment("농장 간단 설명")
    private String description;

    @Comment("도로명 주소")
    private String address;

    @Comment("상세 주소")
    private String addressDetail;

    @Comment("위도")
    private Double latitude;

    @Comment("경도")
    private Double longitude;

    @Comment("농장 상태")
    @Enumerated(EnumType.STRING)
    private FarmStatus status = FarmStatus.OPEN;

    @Comment("체험 비용")
    private int experiencePrice;

    @Comment("팀 최대 인원")
    private Integer maxCapacity;

    @Comment("동일 시간대 최대 예약 가능 팀 수")
    private Integer maxReservation;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "entityId")
    private List<Review> reviewList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordered_farm_id")
    private OrderedFarm orderedFarm;

    @Builder
    public Farm(Long id, String name, Long ownerId, String address, String addressDetail, Double latitude, Double longitude, String description, int experiencePrice, Integer maxCapacity, Integer maxReservation, String businessNumber, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.experiencePrice = experiencePrice;
        this.maxCapacity = maxCapacity;
        this.maxReservation = maxReservation;
        this.businessNumber = businessNumber;
        this.deletedAt = deletedAt;
    }

    public Farm updateFarm(FarmUpdateRequest farmUpdateRequest) {
        this.name = farmUpdateRequest.name();
        this.address = farmUpdateRequest.address();
        this.addressDetail = farmUpdateRequest.addressDetail();
        this.latitude = farmUpdateRequest.latitude();
        this.longitude = farmUpdateRequest.longitude();
        this.description = farmUpdateRequest.description();
        return this;
    }

    public void updateExpPrice(int expPrice) {
        this.experiencePrice = expPrice;
    }

    public void updateMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void updateMaxReservation(Integer maxReservation) {
        this.maxReservation = maxReservation;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
