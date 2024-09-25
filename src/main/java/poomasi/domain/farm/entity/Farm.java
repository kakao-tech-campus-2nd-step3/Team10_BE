package poomasi.domain.farm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "farm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE farm SET deleted = true WHERE id = ?")
@SQLSelect(sql = "SELECT * FROM farm WHERE deleted = false")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // FIXME: owner_id는 Member의 id를 참조해야 합니다.
    @Column(name = "owner_id")
    private Long ownerId;

    private String description;

    private String address; // 도로명 주소
    private String addressDetail; // 상세 주소

    private Double latitude;
    private Double longitude;

    private FarmStatus status = FarmStatus.WAITING;

    private boolean deleted = false;

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

}
