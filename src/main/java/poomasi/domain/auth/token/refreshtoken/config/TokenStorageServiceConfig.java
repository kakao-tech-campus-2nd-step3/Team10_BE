package poomasi.domain.auth.token.refreshtoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import poomasi.domain.auth.token.refreshtoken.service.TokenJpaService;
import poomasi.domain.auth.token.refreshtoken.service.TokenRedisService;
import poomasi.domain.auth.token.refreshtoken.service.TokenStorageService;

@Configuration
public class TokenStorageServiceConfig {

    @Value("${spring.token.storage.type}")
    private String tokenStorageType;

    @Bean
    public TokenStorageService tokenStorageService(TokenRedisService tokenRedisService, TokenJpaService tokenJpaService) {
        if ("redis".equals(tokenStorageType)) {
            return tokenRedisService;
        } else {
            return tokenJpaService;
        }
    }
}