package poomasi.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member_profile")
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 50)
    private String name;

    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Column(nullable = true, length = 255)
    private String address;

    @Column(nullable = true, length = 255)
    private String addressDetail;

    @Column(nullable=true, length=255)
    private Long coordinateX;

    @Column(nullable=true, length=255)
    private Long coordinateY;

    @Column(nullable = true, length = 50)
    private boolean isBanned;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public MemberProfile(String name, String phoneNumber, String address, Member member) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isBanned = false;
        this.createdAt = LocalDateTime.now();
        this.member = member;
    }

    public MemberProfile() {
        this.name = "UNKNOWN"; // name not null 조건 때문에 임시로 넣었습니다. nullable도 true로 넣었는데 안 되네요
        this.createdAt = LocalDateTime.now();
    }

}
