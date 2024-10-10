package poomasi.domain.auth.security.userdetail;

import jdk.jfr.Description;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Description("security context에 저장 될 객체")
@Data
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public UserDetailsImpl(Member member, Map<String, Object> attributes ) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(member.getRole());
            }
        });
        return collection;
    }

    public Role getRole(){
        return member.getRole();
    }

    public String getAuthority() {
        return member.getRole().name();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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

    //Oauth2 member name
    @Override
    public String getName() {
        return null;
    }

    public Member getMember(){
        return member;
    }
}
