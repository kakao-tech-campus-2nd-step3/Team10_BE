package poomasi.domain.order._payment.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.order._payment.dto.request.PaymentPreRegisterRequest;
import poomasi.domain.order._payment.dto.request.PaymentWebHookRequest;
import poomasi.domain.order._payment.service.ProductPaymentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ProductPaymentService productPaymentService;

    @Description("사전 결제 api")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/pre-payment")
    public ResponseEntity<?> postPrepare(PaymentPreRegisterRequest paymentPreRegisterRequest) throws IamportResponseException, IOException {
        return ResponseEntity.ok(
                productPaymentService.portonePrePaymentRegister(paymentPreRegisterRequest)
        );
    }

    @Description("결제 바로 직전 포트원에서 보내는 confirm 요청" + " 결제를 진행하려면 HTTP Status 200 응답, 그렇지 않으면 500 응답 보내기" )
    @PostMapping("/confirm/")
    public ResponseEntity<?> confirmProductStock(@RequestParam String merchantUid, @RequestParam String impUid) throws IamportResponseException, IOException {
        productPaymentService.confirmBeforePayment(merchantUid, impUid);
        return ResponseEntity.ok().build();
    }


    @Description("포트원 웹훅 수신 api")
    @PostMapping("/portone-webhook")
    public void handleIamportWebhook(@RequestBody PaymentWebHookRequest paymentWebHookRequest) throws IamportResponseException, IOException {
        productPaymentService.handlePortOneProductWebhookEvent(paymentWebHookRequest);
    }






    /*

    @GetMapping("/")
    @Secured("ROLE_CUSTOMER")
    @Description("결제 내역 단건 조회")
    public ResponseEntity<?> getPaymentById(Long paymentId){
        PaymentResponse paymentResponse =  paymentService.getPayment(paymentId);
        return ResponseEntity.ok(paymentResponse);
    }


    @Secured("ROLE_CUSTOMER")
    @Description("order에 해당하는 결제 내역 조회")
    public ResponseEntity<?> getPaymentByOrderId(@RequestParam Long orderId){
        PaymentResponse paymentResponse =  paymentService.getPayment(orderId);
        return ResponseEntity.ok(paymentResponse);
    }

    */


}

/**TODO : filter 만들어서 webhook URL에 대해 IP 검증해야 함
 *@Description("포트원 webhook + 동기화")
 @PostMapping("/portone-webhook")
  *  */