package poomasi.domain.auth.security.userdetail;

import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.security.oauth2.dto.response.OAuth2KakaoResponse;
import poomasi.domain.auth.security.oauth2.dto.response.OAuth2Response;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.member.entity.Role;
import poomasi.domain.member.repository.MemberRepository;

import java.util.Map;

@Service
@Slf4j
public class OAuth2UserDetailServiceImpl extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public OAuth2UserDetailServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Response oAuth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {

            String providerId = String.valueOf(oAuth2User.getAttributes().get("id"));
            oAuth2UserInfo = new OAuth2KakaoResponse(
                    providerId,
                    (Map)oAuth2User.getAttributes().get("kakao_account")
            );
        } else{
            log.warn("지원하지 않은 로그인 서비스 입니다.");
        }
        
        // 정보 추출
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_CUSTOMER;
        LoginType loginType = oAuth2UserInfo.getLoginType();
        
        // 카카오 로그인을 처음 한 상태라면 회원가입
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email).orElse(null);
        if(member == null) {
            member = Member.builder()
                    .email(email)
                    .role(role)
                    .loginType(loginType) 
                    .provideId(providerId)
                    .memberProfile(new MemberProfile())
                    .build();

            memberRepository.save(member);
        }

        //있다면 그냥 member 등록하기


        // 카카오 회원으로 로그인이 되어 있다면 -> context에 저장
        return new UserDetailsImpl(member, oAuth2User.getAttributes());
    }

}
