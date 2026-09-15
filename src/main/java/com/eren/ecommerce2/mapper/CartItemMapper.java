package com.eren.ecommerce2.mapper;

import java.math.BigDecimal;

import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.model.CartItem;

public class CartItemMapper {

    // PSEUDOCODE:
    // Kaydedilmiş CartItem entity'sini API response'a çevir.

    // service kısmında cart itemı kaydet
    // kayıtlı cartitemden repsonsu mapper yapsın
    // servic kısmından response olusturma gorevı alınıyor
    // sadece cartı kaydetmesı sağlanıyor.
    public static CartItemResponse toResponse(CartItem cartItem) {

          // Ürünün birim fiyatını al
        BigDecimal price = cartItem.getProduct().getPrice();

        // Sepette kaç tane olduğunu al
        Integer quantity = cartItem.getQuantity();

        // Satır toplamını hesapla:
        // price * quantity
        BigDecimal totalPrice =
                price.multiply(BigDecimal.valueOf(quantity));

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                quantity,
                price,
                totalPrice
        );
    }
}