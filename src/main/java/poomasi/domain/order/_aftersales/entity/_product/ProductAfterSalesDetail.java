package poomasi.domain.order._aftersales.entity._product;

import jakarta.persistence.*;
import jdk.jfr.Description;
import jdk.jfr.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.order.entity._product.OrderedProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Description("상품 판매 후 교환/환불/추소 history")
@Entity
@Getter
@Table(name="product_after_sales_detail")
@NoArgsConstructor
public class ProductAfterSalesDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    @Timestamp
    private LocalDateTime deletedAt;

    @ManyToOne
    private OrderedProduct orderedProduct;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_refund_detail_id", nullable = true) // 외래 키 설정
    private ProductRefundDetail productRefundDetail;
    
    //TODO : payment에 있는 것을 변경해야 함
    private String impUid;

    @Description("환불/교환/취소 금액")
    private BigDecimal adjustAmount;
    
    @Description("취소/교환/환불 수량")
    private Integer adjustmentQuantity;

    @Description("환불/교환/취소 요청 사유")
    private String reason;

    @Enumerated(EnumType.STRING)
    private ProductAfterSalesStatus productAfterSalesStatus;

    @Builder
    public ProductAfterSalesDetail(OrderedProduct orderedProduct,
                                   BigDecimal adjustAmount,
                                   String reason,
                                   Integer adjustmentQuantity,
                                   ProductAfterSalesStatus productAfterSalesStatus) {
        this.orderedProduct = orderedProduct;
        this.adjustAmount = adjustAmount;
        this.reason = reason;
        this.adjustmentQuantity = adjustmentQuantity;
        this.productAfterSalesStatus = productAfterSalesStatus;
    }

    public void setOrderedProduct(OrderedProduct orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    public void setProductAfterSalesStatus(ProductAfterSalesStatus productAfterSalesStatus){
        this.productAfterSalesStatus = productAfterSalesStatus;
    }

    public String getProductRefundDeniedReason(){
        return this.productRefundDetail.getProductRefundDeniedReason();
    }

    public void setProductRefundDeniedReason(String productRefundDeniedReason){
        this.productRefundDetail.setProductRefundDeniedReason(productRefundDeniedReason);
    }

    public void setProductRefundDetail(ProductRefundDetail productRefundDetail) {
        this.productRefundDetail = productRefundDetail;
        productRefundDetail.setProductAfterSalesDetail(this);
    }

    public void changeRefundApproveStatus(String invoiceNumber){
        this.productAfterSalesStatus = ProductAfterSalesStatus.REFUND_APPROVED;
        this.productRefundDetail.setInvoiceNumber(invoiceNumber);
    }

}

