package poomasi.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.member.entity.LoginType;
import poomasi.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndLoginType(String email, LoginType loginType);

    Optional<Member> findByEmail(String email);
}