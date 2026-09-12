package com.eren.ecommerce2.dto;

import java.math.BigDecimal;
public record CartItemResponse(

    Long id, // olusturulan cartitemın ıdsi

    long cartId, // cartitemın ıcınde cartıd- productıd- ve quantity var

    long productId,

    String productName, 

    Integer quantity,

    BigDecimal price

     
) {
    
}
