package poomasi.domain.order.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static poomasi.domain.order.entity.OrderStatus.PENDING;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order extends AbstractOrder{

    @Column(name = "order_product_details_id")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProductDetails> orderProductDetails;

    @OneToOne
    @JoinColumn(name = "order_details_id") // 여기서 JoinColumn 사용
    private OrderDetails orderDetails;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public Order(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(OrderProductDetails orderProductDetails) {
        this.orderProductDetails.add(orderProductDetails);
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


}
