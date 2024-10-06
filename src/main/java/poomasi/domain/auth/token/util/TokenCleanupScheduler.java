package poomasi.domain.auth.token.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.token.blacklist.service.BlacklistJpaService;
import poomasi.domain.auth.token.refreshtoken.service.TokenJpaService;

@Profile("token-jpa") // token-jpa 프로파일이 활성화되었을 때만 활성화, jpa 사용할 떄 application.yml에서 따로 설정 필요
@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {
    private final BlacklistJpaService blacklistJpaService;
    private final TokenJpaService tokenJpaService;

    @Scheduled(fixedRate = 3600000) // 한 시간마다 실행 (1시간 = 3600000 밀리초)
    public void cleanUpBlacklistExpiredTokens() {
        blacklistJpaService.removeExpiredTokens();
    }

    @Scheduled(fixedRate = 86400000) // 하루마다 실행 (24시간 = 86400000 밀리초)
    public void cleanUpTokenExpiredTokens() {
        tokenJpaService.removeExpiredTokens();
    }
}
