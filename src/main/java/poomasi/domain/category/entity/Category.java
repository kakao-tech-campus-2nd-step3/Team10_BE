package poomasi.domain.category.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.category.dto.CategoryRequest;
import poomasi.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void modifyName(CategoryRequest categoryRequest) {
        this.name = categoryRequest.name();
    }

    public void deleteProduct(Product product) {
        this.products.remove(product);
    }

    public void addProduct(Product saveProduct) {
        this.products.add(saveProduct);
    }
}
