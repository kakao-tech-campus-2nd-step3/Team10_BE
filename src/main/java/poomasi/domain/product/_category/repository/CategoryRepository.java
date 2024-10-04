package poomasi.domain.product._category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.product._category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
