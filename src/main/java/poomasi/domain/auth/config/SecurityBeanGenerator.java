package poomasi.domain.auth.config;

import jdk.jfr.Description;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import poomasi.domain.auth.util.JwtUtil;

@Configuration
public class SecurityBeanGenerator {

    @Bean
    @Description("AuthenticationProvider를 위한 Spring bean")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Description("open endpoint를 위한 spring bean")
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    @Description("jwt 토큰 발급을 위한 spring bean")
    JwtUtil jwtProvider() {
        return new JwtUtil();
    }
}

