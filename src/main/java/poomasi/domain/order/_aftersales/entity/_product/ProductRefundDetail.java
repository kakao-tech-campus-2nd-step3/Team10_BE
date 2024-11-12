package poomasi.domain.order._aftersales.entity._product;


import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import poomasi.domain.product.entity.Product;

@Entity
@Table(name= "product_refund_detail")
@Getter
@NoArgsConstructor
public class ProductRefundDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "productRefundDetail") // 주인이 아닌 쪽에 mappedBy 설정
    private ProductAfterSalesDetail productAfterSalesDetail;

    @Description("반품 회수지. 기본 값은 보낸 주소")
    private String pickupLocationAddress;
    
    @Description("반품 회수지 상세 정보")
    private String pickupLocationAddressDetail;

    @Description("반송지. 기본 값은 받은 주소")
    private String returnAddress;

    @Description("반송지. 기본 값은 받은 주소")
    private String returnAddressDetail;

    @Description("반품/교환 시 운송장 번호")
    private String invoiceNumber;
    
    @Description("반품/교환 시 요청 사항")
    private String request;
    
    @Description("환불 거절 사유")
    private String productRefundDeniedReason;

    @Builder
    public ProductRefundDetail(ProductAfterSalesDetail productAfterSalesDetail,
                               String pickupLocationAddress,
                               String pickupLocationAddressDetail,
                               String returnAddress,
                               String returnAddressDetail,
                               String invoiceNumber,
                               String request,
                               String productRefundDeniedReason){
        this.productAfterSalesDetail = productAfterSalesDetail;
        this.pickupLocationAddress = pickupLocationAddress;
        this.pickupLocationAddressDetail = pickupLocationAddressDetail;
        this.returnAddress = returnAddress;
        this.returnAddressDetail = returnAddressDetail;
        this.invoiceNumber = invoiceNumber;
        this.request = request;
        this.productRefundDeniedReason = productRefundDeniedReason;
    }

    public void setProductRefundDeniedReason(String productRefundDeniedReason) {
        this.productRefundDeniedReason = productRefundDeniedReason;
    }

    public void setProductAfterSalesDetail(ProductAfterSalesDetail productAfterSalesDetail) {
        this.productAfterSalesDetail = productAfterSalesDetail;
    }

    public void setInvoiceNumber(String invoiceNumber){
        this.invoiceNumber = invoiceNumber;
    }

}
