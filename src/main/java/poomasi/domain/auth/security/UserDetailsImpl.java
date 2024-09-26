package poomasi.domain.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.entity.Role;

import java.util.ArrayList;
import java.util.Collection;


@NoArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    //private static final long serialVersionUID = 1L;

    private Member member;
    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole()
                        .name();
            }
        });

        return collection;
    }

    public String getAuthority() {
        return member.getRole().name();
    }

    @Override
    public String getPassword() {
        return this.member
                .getPassword();
    }

    @Override
    public String getUsername() {
        return this.member
                .getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
