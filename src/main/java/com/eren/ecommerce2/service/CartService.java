// CartService.java

package com.eren.ecommerce2.service;

import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.dto.CartResponse;
import com.eren.ecommerce2.mapper.CartItemMapper;
import com.eren.ecommerce2.model.Cart;
import com.eren.ecommerce2.model.CartItem;
import com.eren.ecommerce2.repository.CartItemRepository;
import com.eren.ecommerce2.repository.CartRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart createCart() {

        // Yeni, boş bir sepet oluştur.
        Cart cart = new Cart();

        // DB'ye kaydet.
        // id burada oluşur.
        return cartRepository.save(cart);
    }

    public CartResponse getCart(Long cartId) {

        // 1. Böyle bir cart gerçekten var mı?
        cartRepository
                .findById(cartId)
                .orElseThrow();

        // 2. Bu cartId'ye ait bütün CartItem'ları DB'den getir
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        // 3. CartItem entity listesini
        // CartItemResponse listesine çevir
        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(CartItemMapper::toResponse)
                .toList();

        // 4. Her CartItemResponse içindeki totalPrice değerini al
        // ve hepsini topla
        BigDecimal totalPrice = itemResponses.stream()
                .map(CartItemResponse::totalPrice)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        // 5. Bütün sepeti response olarak döndür
        return new CartResponse(
                cartId,
                itemResponses,
                totalPrice);
    }

    
}