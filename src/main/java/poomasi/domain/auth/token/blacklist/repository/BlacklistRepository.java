package poomasi.domain.auth.token.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.auth.token.blacklist.entity.Blacklist;

import java.util.Optional;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Optional<Blacklist> findByKey(String key);
    void deleteByKey(String key);
    boolean existsByKey(String key);
}