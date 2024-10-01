package poomasi.domain.farm.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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

@Entity
@Getter
@Table(name = "farm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE farm SET deleted_at=NOW() WHERE id = ?")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // FIXME: owner_id는 Member의 id를 참조해야 합니다.
    @Comment("농장 소유자 ID")
    @Column(name = "owner_id")
    private Long ownerId;

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

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Farm(String name, Long ownerId, String address, String addressDetail, Double latitude, Double longitude, String description) {
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
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
}
