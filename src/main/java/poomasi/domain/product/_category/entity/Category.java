package poomasi.domain.product._category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.product._category.dto.CategoryRequest;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public void modifyName(CategoryRequest categoryRequest) {
        this.name = categoryRequest.name();
    }
}
