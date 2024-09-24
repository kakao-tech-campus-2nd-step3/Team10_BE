package poomasi.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository <Member, Long> {

}
