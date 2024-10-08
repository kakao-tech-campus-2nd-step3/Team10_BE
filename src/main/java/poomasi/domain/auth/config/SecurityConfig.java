package poomasi.domain.auth.config;

import lombok.AllArgsConstructor;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import poomasi.domain.auth.security.filter.CustomLogoutFilter;
import poomasi.domain.auth.security.filter.CustomUsernamePasswordAuthenticationFilter;
import poomasi.domain.auth.security.filter.JwtAuthenticationFilter;
import poomasi.domain.auth.security.handler.CustomSuccessHandler;
import poomasi.domain.auth.security.userdetail.OAuth2UserDetailServiceImpl;
import poomasi.domain.auth.util.JwtUtil;
import poomasi.domain.auth.security.handler.*;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true , prePostEnabled = false) // 인가 처리에 대한 annotation
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final MvcRequestMatcher.Builder mvc;
    private final CustomSuccessHandler customSuccessHandler;

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

        //cors 해제
        http.cors(AbstractHttpConfigurer::disable);

        //세션 해제
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //기본 로그아웃 해제
        http.logout(AbstractHttpConfigurer::disable);

        // 기본 경로 및 테스트 경로
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.GET, "/api/farm/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/review/**").permitAll()
                .requestMatchers("/api/sign-up", "/api/login", "api/reissue").permitAll()
                .requestMatchers("/api/need-auth/**").authenticated()
                .anyRequest().
                authenticated()
        );

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

        /*
        oauth2 인증은 현재 해제해놨습니다 -> 차후 code를 front에서 어떤 경로로 받을 것인지
        아니면 kakao에서 바로 redirect를 백엔드로 할 지 정해지면
        processing url 작성하겠습니다

        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(oAuth2UserDetailServiceImpl))
                        .successHandler(customSuccessHandler)
                );
         */
        http.oauth2Login(AbstractHttpConfigurer::disable);

        CustomUsernamePasswordAuthenticationFilter customUsernameFilter =
                new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        customUsernameFilter.setFilterProcessesUrl("/api/login");

        http.addFilterAt(customUsernameFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        //http.addFilterAfter(customLogoutFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
    
}




