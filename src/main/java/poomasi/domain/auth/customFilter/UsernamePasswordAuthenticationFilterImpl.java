package poomasi.domain.auth.customFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class UsernamePasswordAuthenticationFilterImpl extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    //TODO : Jwt 주입 받아야 함

    public UsernamePasswordAuthenticationFilterImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Description("authenticationManager 주입")
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Description("인증 시도 메서드")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        //authenticationManager과 UserdetailService를 조회함.
        return authenticationManager.authenticate(authToken);

    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //TODO : access token 발급
        //TODO : refresh token 발급 -> cookie에 넣어서.. 프론트와 협의 봐야 함


        //response도 새로 해야하나? + httpOnly 쿠키 ?
        //필요하다면 refresh token 넣은 cookie dto 만들어서 response에 cookie를 추가하는 방식
        //response.addHeader("Authorization", "Bearer" + token);
    }

    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
