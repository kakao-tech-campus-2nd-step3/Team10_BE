package poomasi.domain.order.entity._product;


import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.order._aftersales.entity._product.ProductAfterSalesDetail;
import poomasi.domain.product.entity.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ordered_products")
@Getter
@NoArgsConstructor
public class OrderedProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_product_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "product_after_sales_detail_id")
    private List<ProductAfterSalesDetail> productAfterSalesDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id")
    private ProductOrder productOrder;

    //FIXME : store Id를 참조해야 한다.
    //나중에 store Id로 변경해야 한다
    //private Store store;
    //private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_description", nullable = true)
    private String productDescription;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Description("구매 당시 1개당 가격")
    private BigDecimal price;
    
    @Description("구매 수량")
    @Column(name="count")
    private Integer count;

    @Description("송장 번호")
    @Column(name = "invoice_number", nullable = true)
    private String invoiceNumber;

    private OrderedProductStatus orderedProductStatus = OrderedProductStatus.PENDING_SELLER_APPROVAL;

    @Description("TODO : product의 delivery fee를 참조해야 한다.")
    private BigDecimal deliveryFee;

    @Description("환불 가능한 남은 수량")
    @Column(name = "refundable_count")
    private Integer adjustableQuantity;

    @Description("취소 된 수량")
    @Column(name = "cacnel_quantity")
    private Integer cancelQuantity;

    @Description("flag가 설정되어 있으면 배송비 환불하지 않아도 된다")
    private boolean isCanceled = false;
    
    // 웹훅 받아서 조회해야 함.
    // findByInvoiceNumber 후 
    // web hook controller 만들어서
    // 배송 상태 적절히 변경해야 함

    @Builder
    public OrderedProduct(Product product, ProductOrder productOrder, String productDescription, String productName, BigDecimal price, Integer count) {
        this.product = product;
        this.productOrder = productOrder;
        this.productDescription = productDescription;
        this.productName = productName;
        this.price = price;
        this.count = count;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setOrderedProductStatus(OrderedProductStatus orderedProductStatus) {
        this.orderedProductStatus = orderedProductStatus;
    }

    public void addProductAfterSalesDetail(ProductAfterSalesDetail productAfterSalesDetail) {
        this.productAfterSalesDetails.add(productAfterSalesDetail);
        productAfterSalesDetail.setOrderedProduct(this);
    }

    public Long getOrderId(){
        return this.productOrder.getId();
    }

    public void subtractRefundableCount(Integer refundableCount) {
        this.adjustableQuantity -= refundableCount;
    }

    public void addCancelQuantity(Integer cancelQuantity) {
        this.isCanceled = true;
        this.cancelQuantity += cancelQuantity;
    }

    public OrderedProductStatus changeOrderedProductStatusToCancel() {
        if (this.count == this.cancelQuantity) {
            this.orderedProductStatus = OrderedProductStatus.CANCELLED;
        }
        return this.orderedProductStatus;
    }

    public String getStoreAddress(){
        //return this.store.getStoreAddress()
        return "TODO : store의 address를 참조해야 함";
    }

    public String getStoreAddressDetail(){
        //return this.store.getStoreAddressDetail()
        return "TODO: store의 address detail을 참조해야 함";
    }


}

