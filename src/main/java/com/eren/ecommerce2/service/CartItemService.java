package com.eren.ecommerce2.service;

import org.springframework.stereotype.Service;

import com.eren.ecommerce2.dto.CartItemRequest;
import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.mapper.CartItemMapper;
import com.eren.ecommerce2.model.Cart;
import com.eren.ecommerce2.model.CartItem;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.CartItemRepository;
import com.eren.ecommerce2.repository.CartRepository;
import com.eren.ecommerce2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    // CartItem'ı en sonda DB'ye kaydetmek için lazım.
    private final CartItemRepository cartItemRepository;

    // request.cartId() ile gerçek Cart nesnesini bulmak için lazım.
    private final CartRepository cartRepository;

    // request.productId() ile gerçek Product nesnesini bulmak için lazım.
    private final ProductRepository productRepository;

    public CartItemResponse addToCart(long cartId,CartItemRequest request) {
        
        // 1. PSEUDOCODE:
        // "Hangi sepete ürün ekliyoruz?"
        // Request'ten gelen cartId ile DB'deki gerçek Cart nesnesini buluyoruz.
        Cart cart = cartRepository
                .findById(cartId)
                .orElseThrow();

        // 2. PSEUDOCODE:
        // "Hangi ürünü sepete ekliyoruz?"
        // Request'ten gelen productId ile DB'deki gerçek Product nesnesini buluyoruz.
        Product product = productRepository
                .findById(request.productId())
                .orElseThrow();

        // 3. PSEUDOCODE:
        // "Yeni bir sepet satırı oluştur."
        // Henüz DB'ye kayıtlı değil, sadece Java nesnesi.
        CartItem cartItem = new CartItem();

        // 4. PSEUDOCODE:
        // requsetteki cart nesnesini cart itemdeki cart kısmına koy

        cartItem.setCart(cart);

        // 5. PSEUDOCODE:
        // equesttekki product nesnesini cart itemdeki product kımına koy
        cartItem.setProduct(product);

        // 6. PSEUDOCODE:
        // "Bu üründen sepette kaç tane var?"
        cartItem.setQuantity(request.quantity());

        // 7. PSEUDOCODE:
        // "Hazırladığımız CartItem'ı DB'ye kaydet."
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // 8. PSEUDOCODE:
        // "Kaydedilmiş entity'yi API'ye dönecek response tipine çevir."
        // return new CartItemResponse(
        // savedCartItem.getId(),
        // savedCartItem.getCart().getId(),
        // savedCartItem.getProduct().getId(),
        // savedCartItem.getProduct().getName(),
        // savedCartItem.getQuantity(),
        // savedCartItem.getProduct().getPrice()
        // );
        // 
        //8. Kaydedilmiş entity'yi response'a çevir ve döndür.
        return CartItemMapper.toResponse(savedCartItem);
    }
}