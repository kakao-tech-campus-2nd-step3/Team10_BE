package poomasi.domain.order.entity;


import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.order._refund.entity.Refund;
import poomasi.domain.product.entity.Product;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_product_details")
@Getter
@NoArgsConstructor
public class OrderProductDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

   /* @OneToOne(fetch = FetchType.LAZY)
    private Refund refund;*/

    @Column(name = "product_description", nullable = true)
    private String productDescription;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Description("구매 당시 1개당 가격")
    private BigDecimal price;

    @Column(name="count")
    private Integer count;

    @Description("송장 번호")
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "review_id")
    Long reviewId;

    //private String sellerName;
    //refund..


    @Builder
    public OrderProductDetails(Product product, Order order, String productDescription, String productName, BigDecimal price, Integer count) {
        this.product = product;
        this.order = order;
        this.productDescription = productDescription;
        this.productName = productName;
        this.price = price;
        this.count = count;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setReviewId(Long reviewId){this.reviewId = reviewId;}
}

