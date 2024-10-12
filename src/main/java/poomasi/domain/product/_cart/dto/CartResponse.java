package poomasi.domain.product._cart.dto;

import java.util.function.Function;
import poomasi.domain.product._cart.entity.Cart;

public record CartResponse (
        String productName,
        Long productPrice,
        Integer productCount,
        Boolean isSelected,
        String farmName
){

}
