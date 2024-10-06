package poomasi.domain.auth.token.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.auth.token.blacklist.entity.Blacklist;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    void deleteByKey(String key);
    Optional<Blacklist> findByKeyAndExpireAtAfter(String key, LocalDateTime now);
    boolean existsByKeyAndExpireAtAfter(String key, LocalDateTime now);
    void deleteAllByExpireAtBefore(LocalDateTime now);

}