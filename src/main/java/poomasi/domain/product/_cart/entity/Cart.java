package poomasi.domain.product._cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean selected;

    private Long memberId;

    private Long productId;

    private Integer count;

    @Builder
    public Cart(Long memberId, Long productId, Boolean selected, Integer count) {
        this.memberId = memberId;
        this.productId = productId;
        this.selected = selected;
        this.count = count;
    }

    public void addCount() {
        this.count += 1;
    }

    public void subCount() {
        this.count -= 1;
    }

    public void changeSelect() {
        this.selected = !this.selected;
    }
}
