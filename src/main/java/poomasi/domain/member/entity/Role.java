package poomasi.domain.member.entity;

import org.springframework.security.core.GrantedAuthority;


//extends GrantedAuthority를 해서 구현한 것도 있던데 . . . 좀 더 찾아봐야겠어요
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,       // 관리자
    ROLE_FARMER,      // 농부 역할
    ROLE_CUSTOMER;     // 구매자 역할

    @Override
    public String getAuthority() {
        return name();
    }
}

