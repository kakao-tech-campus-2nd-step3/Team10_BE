package poomasi.domain.product._cart.dto;

import java.math.BigDecimal;

public record CartResponse(
        Long cartId,
        String productName,
        BigDecimal productPrice,
        Integer productCount,
        Boolean isSelected,
        String farmName
) {

}
