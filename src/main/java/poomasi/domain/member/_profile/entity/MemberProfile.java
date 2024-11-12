package poomasi.domain.member._profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import poomasi.domain.image.entity.Image;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member_profile")
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE member_profile SET deleted_at = current_timestamp WHERE id = ?")
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    @Builder.Default
    private boolean isBanned = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column
    private LocalDateTime deletedAt;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    // 기본 배송지
    @Column(nullable = true, length = 255)
    private String defaultAddress;

    @Column(nullable = true, length = 255)
    private String addressDetail;

    @Column(nullable=true, length=255)
    private Long coordinateX;

    @Column(nullable=true, length=255)
    private Long coordinateY;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        // MemberProfile이 삭제되기 전에 연관된 이미지를 삭제
        if (profileImage != null) {
            profileImage.setDeletedAt(LocalDateTime.now());
        }
    }

    public MemberProfile() {
    }

    public void setAddress(
            String defaultAddress,
            String addressDetail,
            Long coordinateX,
            Long coordinateY) {
        if (defaultAddress != null) this.defaultAddress = defaultAddress;
        if (addressDetail != null) this.addressDetail = addressDetail;
        if (coordinateX != null) this.coordinateX = coordinateX;
        if (coordinateY != null) this.coordinateY = coordinateY;

    }


}
