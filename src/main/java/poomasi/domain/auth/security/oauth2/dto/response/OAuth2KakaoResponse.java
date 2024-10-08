package poomasi.domain.auth.security.oauth2.dto.response;


import poomasi.domain.member.entity.LoginType;

import java.util.Map;

public record OAuth2KakaoResponse(String id, Map<String, Object> attribute) implements OAuth2Response {


    public OAuth2KakaoResponse(String id, Map<String, Object> attribute) {
        this.id = id;
        this.attribute =  attribute;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attribute.get("email"));
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
