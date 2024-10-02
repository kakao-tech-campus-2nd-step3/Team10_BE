package poomasi.domain.member.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,       // 관리자
    ROLE_FARMER,      // 농부 역할
    ROLE_CUSTOMER;     // 구매자 역할

    @Override
    public String getAuthority() {
        return name();
    }
}

