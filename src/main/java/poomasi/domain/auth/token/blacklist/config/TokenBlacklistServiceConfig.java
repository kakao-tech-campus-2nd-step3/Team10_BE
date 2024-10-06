package poomasi.domain.auth.token.blacklist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import poomasi.domain.auth.token.blacklist.service.BlacklistJpaService;
import poomasi.domain.auth.token.blacklist.service.TokenBlacklistService;
import poomasi.domain.auth.token.blacklist.service.BlacklistRedisService;

@Configuration
public class TokenBlacklistServiceConfig {

    @Value("${spring.token.blacklist.type}")
    private String tokenBlacklistType;

    @Bean
    public TokenBlacklistService tokenBlacklistService(BlacklistRedisService blacklistRedisService, BlacklistJpaService blacklistJpaService) {
        if ("redis".equals(tokenBlacklistType)) {
            return blacklistRedisService;
        } else {
            return blacklistJpaService;
        }
    }
}