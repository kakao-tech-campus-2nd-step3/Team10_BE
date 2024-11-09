package poomasi.domain.order.service;

import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.order._payment.dto.request.PaymentPreRegisterRequest;
import poomasi.domain.order._payment.repository.PaymentRepository;
import poomasi.domain.order._payment.service.PaymentService;
import poomasi.domain.order.dto.response.OrderDetailsResponse;
import poomasi.domain.order.dto.response.OrderProductDetailsResponse;
import poomasi.domain.order.dto.response.OrderResponse;
import poomasi.domain.order.entity.Order;
import poomasi.domain.order.entity.OrderDetails;
import poomasi.domain.order.entity.OrderProductDetails;
import poomasi.domain.order.entity.OrderStatus;
import poomasi.domain.order.repository.OrderProductDetailsRepository;
import poomasi.domain.order.repository.OrderRepository;
import poomasi.domain.product._cart.entity.Cart;
import poomasi.domain.product._cart.repository.CartRepository;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static poomasi.domain.order.entity.OrderStatus.AWAITING_SELLER_CONFIRMATION;
import static poomasi.global.error.BusinessError.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final OrderProductDetailsRepository orderProductDetailsRepository;

    @Transactional
    public PaymentPreRegisterRequest preOrderRegister(){
        Member member = getMember();
        Long memberId = member.getId();
        List<Cart> cartList = cartRepository.findByMemberIdAndSelected(memberId);

        //TODO : dto에서 address, address detail 꺼내와야 함. -> dto 확정이 안 나서 임시로 넣음
        String address = "금정구";
        String addressDetails = "수림로";
        String deliveryRequest = "조심히 다뤄 주세요";
        
        Order order = new Order(
                new OrderDetails(address,
                        addressDetails,
                        deliveryRequest)
        );

        //cart에 있는 총 가격 계산하기
        BigDecimal totalPrice = BigDecimal.ZERO;
        
        // cart 돌면서 order details 추가
        for (Cart cart : cartList) {
            Long productId = cart.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND));
            String productDescription = product.getDescription();
            Integer count = cart.getCount();
            String productName = product.getName();
            BigDecimal price = product.getPrice();
            OrderProductDetails orderProductDetails = OrderProductDetails
                    .builder()
                    .product(product)
                    .order(order)
                    .productDescription(productDescription)
                    .productName(productName)
                    .price(price)
                    .count(count)
                    .build();
            order.addOrderDetail(orderProductDetails);
            totalPrice = totalPrice.add(price);
        }
        order.setTotalAmount(totalPrice);
        orderRepository.save(order);

        String merchantUid = order.getMerchantUid();
        return new PaymentPreRegisterRequest(merchantUid, totalPrice);
    }

    @Description("멤버 ID 기반으로 모든 order 다 들고 오는 메서드")
    public List<OrderResponse> findAllOrdersByMemberId(){
        Member member = getMember();
        Long memberId = member.getId();
        List<Order> orderList = orderRepository.findByMemberId(memberId);
        return orderList
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList()
                );
    }

    @Description("멤버 id 기반으로 특정 orderId 들고오는 메서드")
    public OrderResponse findOrderByMemberId(Long orderId){
        Member member = getMember();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ORDER_NOT_FOUND));

        validateOrderOwnership(order, member);
        return OrderResponse.fromEntity(order);
    }


    @Description("orderId 기반으로 order details(주소, 상세주소, 배송 요청 사항 ..등) 들고오는 메서드")
    public OrderDetailsResponse findOrderDetailsByOrderId(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ORDER_NOT_FOUND));
        OrderDetails orderDetails = order.getOrderDetails();

        return OrderDetailsResponse.fromEntity(orderDetails);
    }

    @Description("orderId에 해당하는 order product details 가져오는 메서드")
    public List<OrderProductDetailsResponse> findAllOrderProductDetails(Long orderId){
        Member member = getMember();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ORDER_NOT_FOUND));
        validateOrderOwnership(order, member);
        return order.getOrderProductDetails()
                .stream()
                .map(OrderProductDetailsResponse::fromEntity)
                .collect(Collectors.toList()
                );
    }


    @Description("orderId에 해당하는 order product Details의 단건 조회")
    public OrderProductDetailsResponse findOrderProductDetailsById(Long orderId, Long orderProductDetailsId){
        Member member = getMember();
        OrderProductDetails orderProductDetails = orderProductDetailsRepository.findById(orderProductDetailsId)
                .orElseThrow(()-> new BusinessException(ORDER_PRODUCT_DETAILS_NOT_FOUND));
        Order order = orderProductDetails.getOrder();
        
        // order product details의 주인 order 검사 그리고 , orderId의 주인 member 검사
        validateOrderProductDetailsByOrderId(order, orderId);
        validateOrderOwnership(order, member);
        
        return OrderProductDetailsResponse.fromEntity(orderProductDetails);
    }


    @Description("member의 order인지 검사하는 메서드")
    private void validateOrderOwnership(Order order, Member member) {
        if (!order.getMember().getId().equals(member.getId())) {
            throw new BusinessException(ORDER_NOT_OWNED_EXCEPTION);
        }
    }
    
    @Description("orderId에 해당하는 order Product Details인지 조회하는 메서드")
    private void validateOrderProductDetailsByOrderId(Order order, Long orderId) {
        if(order.getId()!=orderId){
            throw new BusinessException(ORDER_PRODUCT_DETAILS_NOT_OWNED_EXCEPTION);
        }
    }

    @Description("결제가 완료 된 후, 주문 상태 변경하는 메서드. 굳이 없어도 되긴 함.")
    private void completePaymentAndUpdateStatus(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ORDER_NOT_FOUND));
        order.setOrderStatus(AWAITING_SELLER_CONFIRMATION);
    }

    @Description("주문 상태를 변경하는 메서드")
    private void changeOrderStatus(Long orderId, OrderStatus orderStatus){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ORDER_NOT_FOUND));
        order.setOrderStatus(orderStatus);
    }


    @Description("security context에서 member 객체 가져오는 메서드")
    private Member getMember() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        Object impl = authentication.getPrincipal();
        Member member = ((UserDetailsImpl) impl).getMember();
        return member;
    }

}


