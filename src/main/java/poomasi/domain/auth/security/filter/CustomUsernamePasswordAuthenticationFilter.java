package poomasi.domain.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.auth.util.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Description("인증 시도 메서드")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("username - password 기반으로 인증을 시도 합니다 : CustomUsernamePasswordAuthenticationFilter");
        ObjectMapper loginRequestMapper = new ObjectMapper();
        String username = null;
        String password = null;

        try {
            BufferedReader reader = request.getReader();
            Map<String, String> credentials = loginRequestMapper.readValue(reader, Map.class);
            username = credentials.get("username");
            password = credentials.get("password");
            log.info("유저 정보를 출력합니다. username : "+ username + "password : " + password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        log.info("CustomUsernamePasswordAuthenticationFilter : authentication token 생성 완료");
        return this.authenticationManager.authenticate(authToken);

    }

    @Override
    @Description("로그인 성공 시, accessToken과 refreshToken 발급")
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl customUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        String role = customUserDetails.getAuthority();
        Long memberId = customUserDetails.getMember().getId();

        String accessToken = jwtUtil.generateTokenInFilter(username, role, "access", memberId);
        String refreshToken = jwtUtil.generateTokenInFilter(username, role, "refresh", memberId);

        log.info("usename password 기반 로그인 성공 . cookie에 토큰을 넣어 발급합니다.");
        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
        
        // 나중에 주석 해야 함
        PrintWriter out = response.getWriter();
        out.println("access : " + accessToken + ", refresh : " + refreshToken);
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("usename password 기반 로그인 실패. ");
        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
