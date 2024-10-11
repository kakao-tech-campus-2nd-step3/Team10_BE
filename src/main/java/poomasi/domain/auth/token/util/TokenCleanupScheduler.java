package poomasi.domain.auth.token.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.token.blacklist.service.BlacklistJpaService;
import poomasi.domain.auth.token.refreshtoken.service.TokenJpaService;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {
    private final BlacklistJpaService blacklistJpaService;
    private final TokenJpaService tokenJpaService;

    // spring.token.blacklist.type이 "jpa"일 때만 실행
    @Scheduled(fixedRate = 3600000) // 한 시간마다 실행 (1시간 = 3600000 밀리초)
    @ConditionalOnProperty(name = "spring.token.blacklist.type", havingValue = "jpa")
    public void cleanUpBlacklistExpiredTokens() {
        blacklistJpaService.removeExpiredTokens();
    }

    // spring.token.storage.type이 "jpa"일 때만 실행
    @Scheduled(fixedRate = 86400000) // 하루마다 실행 (24시간 = 86400000 밀리초)
    @ConditionalOnProperty(name = "spring.token.storage.type", havingValue = "jpa")
    public void cleanUpTokenExpiredTokens() {
        tokenJpaService.removeExpiredTokens();
    }
}
