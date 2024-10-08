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
import poomasi.domain.member.entity.MemberProfile;
import poomasi.domain.member.entity.Role;
import poomasi.domain.member.repository.MemberRepository;

import java.util.Map;

@Service
@Description("소셜 서비스와 로컬 계정 연동 할 것이라면 여기서 연동 해야 함")
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

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_CUSTOMER;
        LoginType loginType = oAuth2UserInfo.getLoginType();
        
        
        //일단 없으면 가입시키는 쪽으로 구현ㄴ
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member == null) {
            member = Member.builder()
                    .email(email)
                    .role(role)
                    .loginType(loginType) // loginType에 맞게 변경
                    .provideId(providerId)
                    .memberProfile(new MemberProfile())
                    .build();

            memberRepository.save(member);

        }
        
        //있다면 그냥 member 등록하기

        if(member.getLoginType()==LoginType.LOCAL){
            //member.setProviderId(providerId); -> 로그인 시 Id 조회함
        }

        // 카카오 회원으로 로그인이 되어 있다면 -> context에 저장
        return new UserDetailsImpl(member, oAuth2User.getAttributes());
    }

}
