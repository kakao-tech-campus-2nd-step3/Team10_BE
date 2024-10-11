package poomasi.domain.wishlist.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted_at = current_timestamp WHERE id = ?")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("상품")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Comment("등록일시")
    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Comment("삭제일시")
    private LocalDateTime deletedAt;

    @Builder
    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

}
