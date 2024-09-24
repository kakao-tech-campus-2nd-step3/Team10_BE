package poomasi.domain.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 인가 처리에 대한 annotation
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf 해제
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) //jwt token을 위한 server stateless
                .httpBasic(AbstractHttpConfigurer::disable)  //basic login disable
                .formLogin(AbstractHttpConfigurer::disable); //form login disable

        return http.build();

        // 공통작업
        // TODO - UsernamePasswordAuthenticationFilter 상단 JwtAuthenticationCustomFilter 작성
        // TODO - error handling을 위한 AuthenticationEntryPoint , AccessDeniedException 구현
        // TODO - 회원가입
        // TODO - AbstractUserDetailsAuthenticationProvider의 구현체 DaoAuthenticationFilter 만들어야 함

        // TODO - Oauth2.0 login


    }

}
