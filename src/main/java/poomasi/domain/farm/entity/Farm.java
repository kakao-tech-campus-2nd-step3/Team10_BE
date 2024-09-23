package poomasi.domain.farm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "farm")
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

    private String address; // 도로명 주소
    private String addressDetail; // 상세 주소

    private Double latitude;
    private Double longitude;

    private boolean deleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();
}
