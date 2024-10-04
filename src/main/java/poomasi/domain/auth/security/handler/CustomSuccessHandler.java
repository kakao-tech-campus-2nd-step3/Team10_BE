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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Description("TODO : Oauth2.0 로그인이 성공하면 server access, refresh token을 발급하는 메서드")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Oauth2.0 로그인이 성공하면 server access, refresh token을 발급하는 메서드
        //

        response.setHeader("access", "<accessToken>");
        response.addCookie(createCookie("refresh", "<refreshToken>"));
        response.setStatus(HttpStatus.OK.value());

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
