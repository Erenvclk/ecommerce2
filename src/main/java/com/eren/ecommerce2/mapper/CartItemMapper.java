package com.eren.ecommerce2.mapper;

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

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
        );
    }
}