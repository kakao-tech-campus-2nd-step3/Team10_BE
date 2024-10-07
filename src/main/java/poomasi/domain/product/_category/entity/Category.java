package poomasi.domain.product._category.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.product._category.dto.CategoryRequest;
import poomasi.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "categoryId")
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
