package poomasi.domain.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import poomasi.domain.auth.security.filter.CustomLogoutFilter;
import poomasi.domain.auth.security.filter.CustomUsernamePasswordAuthenticationFilter;
import poomasi.domain.auth.security.filter.JwtAuthenticationFilter;
import poomasi.domain.auth.util.JwtUtil;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 인가 처리에 대한 annotation
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final MvcRequestMatcher.Builder mvc;

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

        //Oauth2.0 소셜 로그인 필터 구현


        //jwt 인증 필터 구현
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), CustomUsernamePasswordAuthenticationFilter.class);

        //로그인 filter 구현
        http.addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        //basic login disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        //log out filter 추가
        http.addFilterBefore(new CustomLogoutFilter(), CustomLogoutFilter.class);
        return http.build();

    }
}
