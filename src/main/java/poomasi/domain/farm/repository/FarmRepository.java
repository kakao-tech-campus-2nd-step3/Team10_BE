package poomasi.domain.farm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.farm.entity.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    Page<Farm> findAll(Pageable pageable);
}
