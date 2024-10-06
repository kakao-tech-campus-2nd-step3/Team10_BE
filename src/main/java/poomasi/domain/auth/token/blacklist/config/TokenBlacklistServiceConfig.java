package poomasi.domain.auth.token.blacklist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import poomasi.domain.auth.token.blacklist.service.BlacklistJpaService;
import poomasi.domain.auth.token.blacklist.service.TokenBlacklistService;
import poomasi.domain.auth.token.blacklist.service.BlacklistRedisService;

@Configuration
public class TokenBlacklistServiceConfig {

    // token-redis 프로파일이 활성화된 경우 Redis 기반의 블랙리스트 서비스 사용
    @Bean(name = "TokenRedisBlacklistService")
    @Profile("token-redis")
    public TokenBlacklistService tokenRedisBlacklistService(BlacklistRedisService blacklistRedisService) {
        return blacklistRedisService;
    }

    // token-jpa 프로파일이 활성화된 경우 JPA 기반의 블랙리스트 서비스 사용
    @Bean(name = "TokenJpaBlacklistService")
    @Profile("token-jpa")
    public TokenBlacklistService tokenJpaBlacklistService(BlacklistJpaService blacklistJpaService) {
        return blacklistJpaService;
    }
}