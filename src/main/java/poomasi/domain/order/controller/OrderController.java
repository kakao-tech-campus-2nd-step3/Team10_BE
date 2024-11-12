package poomasi.domain.order.controller;


import com.siot.IamportRestClient.exception.IamportResponseException;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.farm.service.FarmService;
import poomasi.domain.order._payment.dto.request.PaymentPreRegisterRequest;
import poomasi.domain.order._payment.service.ProductPaymentService;
import poomasi.domain.order.dto.request.ProductOrderRegisterRequest;
import poomasi.domain.order.service.FarmOrderService;
import poomasi.domain.order.service.ProductOrderService;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final ProductOrderService productOrderService;
    private final FarmOrderService farmOrderService;
    private final ProductPaymentService productPaymentService;

    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/product/pre-order")
    @Description("product 사전 결제")
    public ResponseEntity<?> createProductPreOrder(@RequestBody ProductOrderRegisterRequest productOrderRegisterRequest) throws IOException, IamportResponseException {
        PaymentPreRegisterRequest paymentPreRegisterRequest = productOrderService.productPreOrderRegister(productOrderRegisterRequest);
        return ResponseEntity.ok(
                productPaymentService.portonePrePaymentRegister(paymentPreRegisterRequest)
        );
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/farm/pre-order")
    @Description("farm 사전 결제")
    public ResponseEntity<?> createFarmPreOrder() throws IOException, IamportResponseException {
        PaymentPreRegisterRequest paymentPreRegisterRequest = productOrderService.farmPreOrderRegister();
        return ResponseEntity.ok(
                productPaymentService.portonePrePaymentRegister(paymentPreRegisterRequest)
        );
    }

    @Description("멤버의 결제 완료가 된 단건 주문 조회. 특정 건만 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getAllOrdersByMember(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                productOrderService.findOrderByMemberId(orderId)
        );
    }

    @Description("멤버의 결제 완료가 된 전체 주문 목록 조회. 전체 주문 목록 조회")
    @GetMapping("/")
    public ResponseEntity<?> getOrdersByMember() {
        return ResponseEntity.ok(
                productOrderService.findAllOrdersByMemberId()
        );
    }

    @Description("어떤 주문 대한 디테일 조회. ex) 주소, 상세주소, 배송 요청 사항 등")
    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetailsByMember(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                productOrderService.findOrderDetailsByOrderId(orderId)
        );
    }


    @Description("어떤 주문에 대한 모든 품목 디테일 조회.ex) 품목 가격, 이름, 등등..")
    @GetMapping("/{orderId}/product/details")
    public ResponseEntity<?> getOrderProductDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                productOrderService.findAllOrderProductDetails(orderId)
        );
    }

    @Description("어떤 주문에 대한 품목의 디테일 단건 조회. ex) 품목 가격, 이름, 등등..")
    @GetMapping("/{orderId}/product/details/{detailsId}")
    public ResponseEntity<?> getOrderProductDetailsByDetailsId(@PathVariable Long orderId, @PathVariable Long detailsId) {
        return ResponseEntity.ok(
                productOrderService.findOrderProductDetailsById(orderId, detailsId)
        );
    }

}




