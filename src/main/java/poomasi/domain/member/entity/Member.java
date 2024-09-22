package poomasi.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true, length = 50)
    private String email;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MemberType memberType;

    @Column(nullable = true)
    private String snsAuthId;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberProfile profile;

    public Member(String email, String password, LoginType loginType, MemberType memberType, String snsAuthId) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.memberType = memberType;
        this.snsAuthId = snsAuthId;
    }

    public void setProfile(MemberProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setMember(this);
        }
    }

}
