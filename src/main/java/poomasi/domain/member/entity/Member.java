package poomasi.domain.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted_at = current_timestamp WHERE id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true, length = 50)
    private String email;

    @Column(nullable = true)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LoginType loginType;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Column(nullable = true)
    private String provideId;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberProfile memberProfile;

    private LocalDateTime deletedAt;

    public Member(String email, String password, LoginType loginType, Role role) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.role = role;
    }

    public Member(String email, Role role){
        this.email = email;
        this.role = role;
    }

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
        if (memberProfile != null) {
            memberProfile.setMember(this);
        }
    }

    @Builder
    public Member(String email, Role role, LoginType loginType, String provideId, MemberProfile memberProfile) {
        this.email = email;
        this.role = role;
        this.loginType = loginType;
        this.provideId = provideId;
        this.memberProfile = memberProfile;
    }

    public boolean isFarmer() {
        return role == Role.ROLE_FARMER;
    }
}
