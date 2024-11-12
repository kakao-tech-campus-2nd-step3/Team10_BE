package poomasi.domain.member._profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.member._profile.entity.MemberProfile;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
}