package poomasi.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;

@Getter
@Entity
@Table(name="member")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE farm SET deleted = true WHERE id = ?")
@SQLSelect(sql = "SELECT * FROM farm WHERE deleted = false")
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
    private Role role;

    @Column(nullable = true)
    private String kakaoAuthId;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberProfile profile;

    public Member(String email, String password, LoginType loginType, Role role, String kakaoAuthId) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.role = role;
        this.kakaoAuthId = kakaoAuthId;
    }

    public void setProfile(MemberProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setMember(this);
        }
    }

}
