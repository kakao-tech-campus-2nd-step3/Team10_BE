package poomasi.domain.product._cart.dto;

public record CartResponse(
        Long cartId,
        String productName,
        Long productPrice,
        Integer productCount,
        Boolean isSelected,
        String farmName
) {

}
