package poomasi.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
