package poomasi.domain.order.entity._abstract;

import jakarta.persistence.*;
import jdk.jfr.Description;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.member.entity.Member;
import poomasi.domain.order._payment.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
@SuperBuilder // 빌더 패턴을 사용하도록 설정
@NoArgsConstructor
public abstract class AbstractOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Description("주문 한 사람을 참조한다.")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    @Timestamp
    private LocalDateTime deletedAt;

    @Column(name = "total_amount")
    @Description("총 결제 금액")
    private BigDecimal totalAmount;

    public void setCheckSum(BigDecimal checkSum) {
        this.payment.setCheckSum(checkSum);
    }

    public void subtractChecksum(BigDecimal checkSum) {
        this.payment.subtractCheckSum(checkSum);
    }

    public BigDecimal getCheckSum(){
        return this.payment.getCheckSum();
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


}

