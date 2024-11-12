package poomasi.domain.order.entity._product;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product_order_details")
@Getter
@NoArgsConstructor
public class ProductOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "productOrderDetails", cascade = CascadeType.ALL) // 필드명으로 지정
    private ProductOrder productOrder;

    @Column(name = "return_address")
    @Description("도착 주소")
    private String destinationAddress;

    @Column(name = "destination_address_detail")
    @Description("도착 상세 주소")
    private String destinationAddressDetail;

    @Description("배송 요청 사항")
    @Column(name = "delivery_request", length = 255)
    private String deliveryRequest;


    @Builder
    public ProductOrderDetails(ProductOrder productOrder, String destinationAddress, String destinationAddressDetail, String deliveryRequest) {
        this.productOrder = productOrder;
        this.destinationAddress = destinationAddress;
        this.destinationAddressDetail = destinationAddressDetail;
        this.deliveryRequest = deliveryRequest;
    }

    public void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }

}
