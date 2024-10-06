package poomasi.domain.auth.token.blacklist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.token.blacklist.entity.Blacklist;
import poomasi.domain.auth.token.blacklist.repository.BlacklistRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlacklistJpaService implements TokenBlacklistService{
    private final BlacklistRepository blacklistRepository;

    @Override
    @Transactional
    public void setBlackList(String key, String data, Duration duration) {
        LocalDateTime expireAt = LocalDateTime.now().plusSeconds(duration.getSeconds());

        Blacklist blacklist = new Blacklist();
        blacklist.setKey(key);
        blacklist.setData(data);
        blacklist.setExpireAt(expireAt);

        blacklistRepository.save(blacklist);
    }

    @Override
    public Optional<String> getBlackList(String key) {
        return blacklistRepository.findByKeyAndExpireAtAfter(key, LocalDateTime.now())
                .map(Blacklist::getData);
    }

    @Override
    @Transactional
    public void deleteBlackList(String key) {
        blacklistRepository.deleteByKey(key);
    }

    @Override
    public boolean hasKeyBlackList(String key) {
        return blacklistRepository.existsByKeyAndExpireAtAfter(key, LocalDateTime.now());
    }

    @Transactional
    public void removeExpiredTokens() {
        blacklistRepository.deleteAllByExpireAtBefore(LocalDateTime.now());
    }
}
