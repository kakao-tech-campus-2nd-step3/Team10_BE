package poomasi.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

import static poomasi.domain.member.entity.LoginType.LOCAL;

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
    private String kakaoAuthId;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberProfile profile;

    private LocalDateTime deletedAt;

    public Member(String email, String password, LoginType loginType, Role role) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.role = role;
    }

    public void setProfile(MemberProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setMember(this);
        }
    }

    public void kakaoToLocal(String password) {
        this.password = password;
        this.loginType = LOCAL;
    }

    public boolean isFarmer() {
        return role == Role.ROLE_FARMER;
    }
}
