package poomasi.domain.auth.token.refreshtoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.token.refreshtoken.entity.RefreshToken;
import poomasi.domain.auth.token.refreshtoken.repository.TokenRepository;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenJpaService implements TokenStorageService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void setValues(String key, String data, Duration duration) {
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setKey(key);
        tokenEntity.setData(data);
        // 여기에서 만료 시간을 관리하기 위한 로직을 추가할 수 있음
        tokenRepository.save(tokenEntity);
    }

    @Override
    public Optional<String> getValues(String key, String data) {
        return tokenRepository.findByKey(key).map(RefreshToken::getData);
    }

    @Override
    @Transactional
    public void removeRefreshTokenById(final Long memberId) {
        tokenRepository.deleteAllByData(String.valueOf(memberId));
    }
}
