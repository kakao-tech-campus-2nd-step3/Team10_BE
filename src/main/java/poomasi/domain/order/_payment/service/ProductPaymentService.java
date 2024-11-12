package poomasi.domain.order._payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.order._payment.config.IamportConfig;
import poomasi.domain.order._payment.dto.request.PaymentPreRegisterRequest;
import poomasi.domain.order._payment.dto.request.PaymentWebHookRequest;
import poomasi.domain.order._payment.dto.response.PaymentPreRegisterResponse;
import poomasi.domain.order._payment.dto.response.PaymentResponse;
import poomasi.domain.order._payment.entity.Payment;
import poomasi.domain.order._payment.repository.PaymentRepository;
import poomasi.domain.order._payment.util.PaymentUtil;
import poomasi.domain.order.entity._product.OrderedProduct;
import poomasi.domain.order.entity._product.ProductOrder;
import poomasi.domain.order.repository.ProductOrderRepository;
import poomasi.domain.product._cart.service.CartService;
import poomasi.domain.product.entity.Product;
import poomasi.global.error.BusinessException;
import poomasi.global.error.PaymentConfirmError;
import poomasi.global.error.PaymentConfirmException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static poomasi.domain.order.entity.PaymentStatus.*;
import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPaymentService{

    @Autowired
    private final PaymentRepository paymentRepository;
    private final ProductOrderRepository productOrderRepository;
    private final PaymentUtil paymentUtil;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AtomicBoolean isWebhookReceived = new AtomicBoolean(false); // 웹훅 수신 여부 체크
    //private final ThreadLocal<AtomicBoolean> isWebhookReceived = ThreadLocal.withInitial(AtomicBoolean::new); -> thread local로 제어


    @Description("사전 결제 등록. 프론트엔드에게 서버 merchant uid를 return 해야 함")
    public PaymentPreRegisterResponse portonePrePaymentRegister(PaymentPreRegisterRequest paymentPreRegisterRequest) throws IOException, IamportResponseException {

        String merchantUid = paymentPreRegisterRequest.merchantUid();
        BigDecimal amount = paymentPreRegisterRequest.amount();

        paymentUtil.sendPrepareData(merchantUid, amount);
        return PaymentPreRegisterResponse.from(
                paymentPreRegisterRequest.merchantUid()
        );
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Description("포트원 결제 직전 바로 받는 confirm 요청. 40초 대기")
    public void confirmBeforePayment(String impUid, String merchantUid) throws IOException, IamportResponseException {

        ProductOrder productOrder = productOrderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new BusinessException(PAYMENT_NOT_FOUND));

        List<OrderedProduct> orderedProductList = productOrder.getOrderedProducts();
        //수량 검증
        for(OrderedProduct orderedProduct : orderedProductList) {
            Product product = orderedProduct.getProduct();
            Integer remainQuantity = product.getStock();
            Integer orderQuantity = orderedProduct.getCount();

            //주문 재고가 남은 재고보다 많다면 500 + cancelReason 보내야 함
            if(orderQuantity > remainQuantity){
                throw new PaymentConfirmException(PaymentConfirmError.PAYMENT_PROUCT_CONFIRM_EXCEPTION);
            }
        }

        //결제 되어야 할 금액
        BigDecimal amountToBePaid = productOrder.getTotalAmount();

        /*
        * 1. 200ok 보내기
        * 2. 타이머 세팅 후
        * 타이머 타임 아웃 되면(웹훅을 받지 못하면) 결제 단건 api 호출
        * 만약 웹훅을 받으면 받은 데이터에서 getImpUid후, 결제 단건 호출 및 타이머 초기화
        * */

        //재고 검증 완료 -> 200 OK 보내야 함 + 웹훅 수신 여부에 따라 분기
        scheduler.schedule(() -> {
            try {
                if (!isWebhookReceived.get()) { // 웹훅 수신 못 받으면 다시 보내기
                    if(paymentUtil.validatePaymentAmount(impUid, amountToBePaid)){
                        productOrder.setPaymentStatus(PAYMENT_COMPLETE);
                        decreaseStock(productOrder); //재고 차감
                    }else{
                        paymentUtil.cancelPaymentByImpUid(impUid);  //실제 결제 된 금액과 결제 되어야 할 금액이 다르다면 -> 결제 취소 api를 호출해야 한다.
                        productOrder.setPaymentStatus(PAYMENT_DECLINED);
                        throw new BusinessException(PAYMENT_AMOUNT_MISMATCH);
                    }
                }
            } catch (IOException | IamportResponseException e) {
                log.error(e.getMessage(), e);
                throw new BusinessException(PAYMENT_BAD_REQUEST);
            }
        }, 40, TimeUnit.SECONDS);

    }

    @Description("웹훅 처리 service -> 결제 정상적으로 성공됨을 보장")
    public void handlePortOneProductWebhookEvent(PaymentWebHookRequest paymentWebHookRequest) throws IOException, IamportResponseException {

        isWebhookReceived.set(true); //웹훅 수신 플래그 설정하기

        String impUid = paymentWebHookRequest.impUid();
        String merchantUid = paymentWebHookRequest.merchantUid();
        ProductOrder productOrder = productOrderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new BusinessException(PAYMENT_NOT_FOUND));
        BigDecimal amountToBePaid = productOrder.getTotalAmount();

        //결제 되어야 할 금액과 결제 된 금액이 같다면
        if(paymentUtil.validatePaymentAmount(impUid, amountToBePaid)){
            try{
                decreaseStock(productOrder);
                productOrder.setPaymentStatus(PAYMENT_COMPLETE);
            }catch(BusinessException businessException){
                productOrder.setPaymentStatus(PAYMENT_INSUFFICIENT_QUANTITY);
                throw new BusinessException(PAYMENT_BAD_REQUEST);
            }
        }else{
            paymentUtil.cancelPaymentByImpUid(impUid);  //실제 결제 된 금액과 결제 되어야 할 금액이 다르다면 -> 결제 취소 api를 호출해야 한다.
            productOrder.setPaymentStatus(PAYMENT_DECLINED);
            throw new BusinessException(PAYMENT_AMOUNT_MISMATCH);
        }
    }

    @Description("재고 차감 메서드. 감소하다 exception이 일어나면 rollback하고 결제 취소 해야 함")
    @Transactional(isolation =  Isolation.SERIALIZABLE)
    public void decreaseStock(ProductOrder productOrder){
        List<OrderedProduct> orderedProductList = productOrder.getOrderedProducts();
        for (OrderedProduct orderedProduct : orderedProductList){
            Product product = orderedProduct.getProduct();
            Integer remainQuantity = product.getStock(); //남은 수량
            Integer subtractQuantity = orderedProduct.getCount();//빼야 할 수량
            if(subtractQuantity > remainQuantity){
                throw new BusinessException(STOCK_QUANTITY_EXCEEDED);
            }
            product.subtractStock(subtractQuantity);
        }
    }


    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(PAYMENT_NOT_FOUND));
        return PaymentResponse.fromEntity(payment);
    }
    
    @Description("orderID로 결제 방법 찾는 메서드")
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Member member = getMember();
        Payment payment = paymentRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(PAYMENT_NOT_FOUND));
        return PaymentResponse.fromEntity(payment);
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        Object impl = authentication.getPrincipal();
        Member member = ((UserDetailsImpl) impl).getMember();
        return member;
    }

}


