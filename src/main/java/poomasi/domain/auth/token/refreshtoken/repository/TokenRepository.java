package poomasi.domain.auth.token.refreshtoken.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.auth.token.refreshtoken.entity.RefreshToken;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteAllByData(String Data);
    void deleteAllByExpireAtBefore(LocalDateTime now);
    Optional<RefreshToken> findByKeyAndExpireAtAfter(String key, LocalDateTime now);
}