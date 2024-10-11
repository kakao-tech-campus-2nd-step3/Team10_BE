package poomasi.domain.auth.security.oauth2.dto.response;

import poomasi.domain.member.entity.LoginType;

public interface OAuth2Response {
    LoginType getLoginType();
    String getProviderId();
    String getEmail();
    String getName();
}
