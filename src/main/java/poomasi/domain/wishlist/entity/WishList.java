package poomasi.domain.wishlist.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted_at = current_timestamp WHERE id = ?")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

}
