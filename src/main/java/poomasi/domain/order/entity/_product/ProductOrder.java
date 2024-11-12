package poomasi.domain.order.entity._product;


import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import poomasi.domain.member.entity.Member;
import poomasi.domain.order.entity.PaymentStatus;
import poomasi.domain.order.entity._abstract.AbstractOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_order")
@Getter
@SuperBuilder
@SQLDelete(sql = "UPDATE product_order SET deleted_at = current_timestamp WHERE id = ?")
public class ProductOrder extends AbstractOrder {

    @Column(name = "merchant_uid")
    @Description("서버 내부 주문 id(아임포트 id)")
    private String merchantUid = "p" + new Date().getTime();

    @Column(name = "ordered_products_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderedProduct> orderedProducts;

    @OneToOne
    @JoinColumn(name = "product_order_details_id") // 외래 키 지정
    @Description("상품 배송지, 요청 사항")
    private ProductOrderDetails productOrderDetails;

    public ProductOrder(){

    }

    public void addOrderedProduct(OrderedProduct orderedProduct) {
        this.orderedProducts.add(orderedProduct);
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }

    public String getImpUid(){
        return this.getPayment().getImpUid();
    }

    public PaymentStatus getPaymentStatus(){
        return this.getPayment().getPaymentStatus();
    }

    public void setPaymentStatus(PaymentStatus paymentStatus){
        this.getPayment().setPaymentStatus(paymentStatus);
    }

    public void setProductOrderDetails(ProductOrderDetails productOrderDetails){
        this.productOrderDetails = productOrderDetails;
        productOrderDetails.setProductOrder(this);
    }

}
