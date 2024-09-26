package poomasi.domain.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.thymeleaf.spring6.expression.Mvc;
import poomasi.domain.auth.customFilter.JwtAuthenticationFilter;
import poomasi.domain.auth.customFilter.LogoutFilterImpl;
import poomasi.domain.auth.customFilter.UsernamePasswordAuthenticationFilterImpl;

// TODO - UsernamePasswordAuthenticationFilter 상단 JwtAuthenticationCustomFilter 작성
// TODO - error handling을 위한 AuthenticationEntryPoint , AccessDeniedException 구현
// TODO - 회원가입
// TODO - AbstractUserDetailsAuthenticationProvider의 구현체 DaoAuthenticationFilter 만들어야 함
// TODO - Oauth2.0 login

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 인가 처리에 대한 annotation
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // TODO : 나중에 허용될 endpoint가 많아지면 whiteList로 관리 예정
        // 임시로 GET : [api/farms, api/products, api/login, api/signup, /]은 열어둠
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.GET, "/api/farms/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers("/api/login", "/", "/api/signup").permitAll()
                .anyRequest().
                authenticated()
        );

        //csrf 해제
        http.csrf(AbstractHttpConfigurer::disable);

        //cors 해제
        http.cors(AbstractHttpConfigurer::disable);

        //session 해제 -> jwt token 로그인
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        //jwt 인증 필터 구현
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilterImpl.class);

        //로그인 filter 구현
        http.addFilterAt(new UsernamePasswordAuthenticationFilterImpl(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        //form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        //basic login disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        //log out filter 추가
        http.addFilterBefore(new LogoutFilterImpl(), LogoutFilter.class);
        return http.build();

    }
}
