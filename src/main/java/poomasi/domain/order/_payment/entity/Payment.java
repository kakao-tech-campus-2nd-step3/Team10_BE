package poomasi.domain.order._payment.entity;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Getter;
import poomasi.domain.order.entity.PaymentStatus;
import poomasi.domain.order.entity._farm.FarmOrder;
import poomasi.domain.order.entity._product.ProductOrder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imp_uid")
    @Description("아임포트 결제 imp_uid")
    private String impUid;

    @OneToOne(mappedBy = "payment")
    private ProductOrder productOrder;

    @Description("포트원 결제 금액")
    private BigDecimal totalPrice;

    @Description("할인 가격")
    private BigDecimal discountPrice;

    @Description("사용 포인트")
    private BigDecimal usedPoint;

    @Description("배송비")
    private BigDecimal deliveryFee;

    @Description("최종 가격")
    private BigDecimal finalPrice;
        
    @Description("결제 방식")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Description("checksum")
    private BigDecimal checkSum;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_PENDING;

    public void setCheckSum(BigDecimal checksum) {
        this.checkSum = checksum;
    }

    public void subtractCheckSum(BigDecimal checksum) {
        this.checkSum = this.checkSum.subtract(checksum);
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


}
