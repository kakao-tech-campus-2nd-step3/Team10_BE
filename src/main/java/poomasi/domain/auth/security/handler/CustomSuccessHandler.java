package poomasi.domain.auth.security.handler;

/*
 * TODO : Oauth2.0 로그인이 성공하면 access, refresh를 발급해야 함.
 *
 * */

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.auth.token.util.JwtUtil;
import poomasi.domain.member.entity.Member;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Description("TODO : Oauth2.0 로그인이 성공하면 server access, refresh token을 발급하는 메서드")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("[Oauth2 success handler] - OAuth2 로그인 성공적으로 완료되었습니다. access/refresh token 발급합니다.");

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        Member member = userDetailsImpl.getMember();
        Long memberId = member.getId();

        String accessToken = jwtUtil.generateAccessTokenById(memberId);
        String refreshToken = jwtUtil.generateRefreshTokenById(memberId);

        response.addCookie(createCookie("refresh", refreshToken));
        response.addCookie(createCookie("access", accessToken));

        response.setStatus(HttpStatus.OK.value());

        response.getWriter();
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
