package poomasi.domain.order.entity._farm;

import jakarta.persistence.*;
import jdk.jfr.Description;
import org.hibernate.annotations.Comment;
import poomasi.domain.order._payment.entity.Payment;
import poomasi.domain.order.entity._abstract.AbstractOrder;

import java.util.Date;

//@Entity
//@Table(name = "farm_order")
public class FarmOrder extends AbstractOrder {
    /*
    @OneToOne(fetch=FetchType.LAZY)
    private FarmOrderDetails farmOrderDetails;

    @Column(name = "owner_id")
    private Long ownerId;

    @Comment("농장 간단 설명")
    private String description;

    @Comment("도로명 주소")
    private String destinationAddress;

    @Comment("상세 주소")
    private String addressDetail;

    @Comment("위도")
    private Double latitude;

    @Comment("경도")
    private Double longitude;
    */

    @Column(name = "merchant_uid")
    @Description("서버 내부 주문 id(아임포트 id)")
    private String merchantUid = "f" + new Date().getTime();


}

