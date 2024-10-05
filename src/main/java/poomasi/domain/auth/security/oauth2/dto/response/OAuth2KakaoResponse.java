package poomasi.domain.auth.security.oauth2.dto.response;


import poomasi.domain.member.entity.LoginType;

import java.util.Map;

public record OAuth2KakaoResponse(Map<String, Object> attribute) implements OAuth2Response {

    public OAuth2KakaoResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public LoginType getLoginType(){
        return LoginType.KAKAO;
    }

}
