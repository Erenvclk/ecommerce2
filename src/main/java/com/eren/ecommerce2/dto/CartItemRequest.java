package com.eren.ecommerce2.dto;

public record CartItemRequest(

        // // Hangi sepete ürün ekliyoruz?
        // Long cartId,
        //artık bu kısımı  urlden alıcak controller

        // Hangi ürünü sepete ekliyoruz?
        Long productId,

        // Bu üründen kaç tane ekliyoruz?
        Integer quantity

) {
}