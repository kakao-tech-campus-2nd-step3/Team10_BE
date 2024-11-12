package poomasi.domain.order._aftersales.controller;


import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.order._aftersales.dto.cancel.request.FarmCancelRequest;
import poomasi.domain.order._aftersales.dto.cancel.request.ProductCancelRequest;
import poomasi.domain.order._aftersales.dto.refund.request.ProductRefundRequest;
import poomasi.domain.order._aftersales.dto.refund.request.ProductRefundRequestApprovalRequest;
import poomasi.domain.order._aftersales.dto.refund.request.ProductRefundRequestDeniedRequest;
import poomasi.domain.order._aftersales.service.FarmAfterSalesService;
import poomasi.domain.order._aftersales.service.ProductAfterSalesService;

import java.io.IOException;

@RestController
@RequestMapping("/api/aftersales")
@RequiredArgsConstructor
public class AfterSalesController {

    private final ProductAfterSalesService productAfterSalesService;
    private final FarmAfterSalesService farmAfterSalesService;

    //-------------------------product cancel---------------------//
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/product/cancel")
    public ResponseEntity<?> productCancel(@RequestBody ProductCancelRequest productCancelRequest) throws IOException, IamportResponseException {
        return ResponseEntity.ok(
                productAfterSalesService.cancel(productCancelRequest)
        );
    }

    //-------------------------product refund---------------------//
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/refund-request")
    public ResponseEntity<?> requestRefund(@RequestBody ProductRefundRequest productRefundRequest) {
        return ResponseEntity.ok(
                productAfterSalesService.
                        createRefundRequest(productRefundRequest)
        );
    }

    @Secured({"ROLE_FARMER"})
    @PostMapping("/approve-refund-request")
    public ResponseEntity<?> approveRefundRequest(@RequestBody ProductRefundRequestApprovalRequest productRefundRequestApprovalRequest) throws IOException, IamportResponseException {
        return ResponseEntity.ok(
                productAfterSalesService.processRefundApproval(productRefundRequestApprovalRequest)
        );
    }


    @Secured({"ROLE_FARMER"})
    @PostMapping("/deniedrefund-request")
    public ResponseEntity<?> deniedRefundRequest(@RequestBody ProductRefundRequestDeniedRequest productRefundRequestDeniedRequest) {
        return ResponseEntity.ok(
                productAfterSalesService.processRefundDenied(productRefundRequestDeniedRequest)
        );
    }


    //-------------------------farm cancel---------------------//
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER"})
    @PostMapping("/farm/cancel")
    public ResponseEntity<?> farmCancel(@RequestBody FarmCancelRequest farmCancelRequest) throws IOException, IamportResponseException {
        return ResponseEntity.ok(
                farmAfterSalesService.farmCancel(farmCancelRequest)
        );
    }
    
    
    //------------웹훅 api 받아서 해야 함---------//





}
