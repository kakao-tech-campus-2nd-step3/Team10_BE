package poomasi.domain.auth.config;

import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import poomasi.domain.auth.token.util.JwtUtil;
import poomasi.domain.auth.token.refreshtoken.service.TokenRedisService;

@RequiredArgsConstructor
@Configuration
public class SecurityBeanGenerator {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;
    private final RedisConnectionFactory redisConnectionFactory;
    private final TokenRedisService tokenRedisService;

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
    
    /*
    * jwt util spring bean 등록해야 함
    * */
    <String, Object>
    @Bean
    JwtUtil jwtUtil(){
        return new JwtUtil(tokenRedisService);
    }

}
