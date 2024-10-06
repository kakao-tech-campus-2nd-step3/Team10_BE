package poomasi.domain.auth.token.refreshtoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import poomasi.domain.auth.token.refreshtoken.service.TokenJpaService;
import poomasi.domain.auth.token.refreshtoken.service.TokenRedisService;
import poomasi.domain.auth.token.refreshtoken.service.TokenStorageService;

@Configuration
public class TokenStorageServiceConfig {

    // token-redis 프로파일이 활성화된 경우 Redis 기반의 스토리지 서비스 사용
    @Bean
    @Profile("token-redis")
    public TokenStorageService tokenRedisService(TokenRedisService tokenRedisService) {
        return tokenRedisService;
    }

    // token-jpa 프로파일이 활성화된 경우 Jpa 기반의 스토리지 서비스 사용
    @Bean
    @Profile("token-jpa")
    public TokenStorageService tokenJpaService(TokenJpaService tokenJpaService) {
        return tokenJpaService;
    }
}