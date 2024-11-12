package poomasi.domain.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import poomasi.domain.auth.security.filter.CustomUsernamePasswordAuthenticationFilter;
import poomasi.domain.auth.security.filter.JwtAuthenticationFilter;
import poomasi.domain.auth.security.handler.CustomSuccessHandler;
import poomasi.domain.auth.security.userdetail.OAuth2UserDetailServiceImpl;
import poomasi.domain.auth.security.userdetail.UserDetailsServiceImpl;
import poomasi.domain.auth.token.util.JwtUtil;

import java.util.Arrays;
import java.util.Collections;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false) // 인가 처리에 대한 annotation
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final MvcRequestMatcher.Builder mvc;
    private final CustomSuccessHandler customSuccessHandler;
    private final UserDetailsServiceImpl userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private OAuth2UserDetailServiceImpl oAuth2UserDetailServiceImpl;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Description("순서 : Oauth2 -> jwt -> login -> logout")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        //basic login disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        //csrf 해제
        http.csrf(AbstractHttpConfigurer::disable);

        //cors 설정
        http.cors(cors -> cors
                .configurationSource(corsConfigurationSource));

        //세션 해제
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //기본 로그아웃 해제
        http.logout(AbstractHttpConfigurer::disable);


        // 기본 경로 및 테스트 경로
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST, "/api/farm/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/review/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/image/**").permitAll()
                .requestMatchers("/api/member/sign-up", "/api/login", "api/reissue", "api/payment/**", "api/order/**", "api/reservation/**", "/api/v1/farmer/reservations").permitAll()
                .requestMatchers("/api/need-auth/**").authenticated()
                .anyRequest().
                authenticated()
        );


        //endpoint : {domain}/oauth2/authentication/kakao
//        http
//                .oauth2Login((oauth2) -> oauth2
//                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
//                                .userService(oAuth2UserDetailServiceImpl))
//                        .successHandler(customSuccessHandler)
//                );

        CustomUsernamePasswordAuthenticationFilter customUsernameFilter =
                new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        customUsernameFilter.setFilterProcessesUrl("/api/login");

        http.addFilterAt(customUsernameFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        /*
        로그아웃 필터 등록하기
        LogoutHandler[] handlers = {
                new CookieClearingLogoutHandler(),
                new ClearAuthenticationHandler()
        };
        CustomLogoutFilter customLogoutFilter = new CustomLogoutFilter(jwtUtil, new CustomLogoutSuccessHandler(), handlers);
        customLogoutFilter.setFilterProcessesUrl("/api/logout");
        customLogoutFilter.
        http.addFilterAt(customLogoutFilter, LogoutFilter.class);

        http.logout( (logout) ->
                logout.
                        logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .addLogoutHandler(new CookieClearingLogoutHandler())
                        .addLogoutHandler(new ClearAuthenticationHandler())
        );
        */

        //http.addFilterAfter(customLogoutFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

}




