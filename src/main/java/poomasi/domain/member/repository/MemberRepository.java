package poomasi.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByIdAndDeletedAtIsNull(Long id);
}
