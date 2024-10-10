package poomasi.domain.auth.security.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("[logout success handler] - cookie 제거");
        expireCookie(response, "access");
        expireCookie(response, "refresh");
    }

    private void expireCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null); // 쿠키를 null로 설정
        cookie.setMaxAge(0); // 쿠키의 최대 생명 주기를 0으로 설정
        cookie.setPath("/"); // 쿠키의 경로를 설정 (원래 설정한 경로와 동일하게)
        response.addCookie(cookie); // 응답에 쿠키 추가
    }
}
