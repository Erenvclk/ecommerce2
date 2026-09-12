package com.eren.ecommerce2.dto;

public record CartItemRequest(

        // Hangi sepete ürün ekliyoruz?
        Long cartId,

        // Hangi ürünü sepete ekliyoruz?
        Long productId,

        // Bu üründen kaç tane ekliyoruz?
        Integer quantity

) {
}