package com.eren.ecommerce2.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eren.ecommerce2.dto.CartItemRequest;
import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.dto.UpdateCartItemRequest;
import com.eren.ecommerce2.exception.StockExceededException;
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

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemResponse addToCart(
            Long cartId,
            CartItemRequest request) {

        // 1. URL'den gelen cartId ile Cart'ı bul
        Cart cart = cartRepository
                .findById(cartId)
                .orElseThrow();

        // 2. Request içindeki productId ile Product'ı bul
        Product product = productRepository
                .findById(request.productId())
                .orElseThrow();

        // 3. Bu cart + product için daha önce CartItem oluşturulmuş mu?
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(
                cartId,
                request.productId());

        CartItem cartItem;

        // 4. Varsa mevcut CartItem'ı kullan
        if (existingItem.isPresent()) {

            cartItem = existingItem.get();

            // // Mevcut quantity + request'ten gelen quantity
            // cartItem.setQuantity(
            // cartItem.getQuantity() + request.quantity()
            // );
            // Sepette zaten kaç tane var?
            int currentQuantity = cartItem.getQuantity();

            // Kullanıcı kaç tane daha eklemek istiyor?
            int requestedQuantity = request.quantity();

            // Eklemeden sonraki toplam
            int newQuantity = currentQuantity + requestedQuantity;

            // Ürünün gerçek stok miktarı
            int stock = product.getStock();

            // Stok aşılırsa burada hata fırlatacağız

            if (newQuantity > stock) {
                throw new StockExceededException(product.getStock());
            }

            cartItem.setQuantity(newQuantity);

        } else {

            // Ürün sepette hiç yoksa,
            // request quantity direkt stock ile karşılaştırılır.
            if (request.quantity() > product.getStock()) {
                throw new StockExceededException(product.getStock());
            }

            // 5. Yoksa yeni CartItem oluştur
            cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.quantity());
        }

        // 6. Güncellenmiş veya yeni CartItem'ı kaydet
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // 7. Response'a çevir
        return CartItemMapper.toResponse(savedCartItem);
    }

    public CartItemResponse updateQuantity(
            Long cartId,
            Long itemId,
            UpdateCartItemRequest request) {

        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow();

        if (cartItem.getCart().getId() != cartId) {
            throw new RuntimeException("CartItem bu sepete ait değil");
        }

        if (request.quantity() <= 0) {
            throw new RuntimeException("Quantity 0'dan büyük olmalı");
        }

        Integer stock = cartItem
                .getProduct()
                .getStock();

        if (request.quantity() > stock) {
            throw new StockExceededException(stock);
        }

        cartItem.setQuantity(request.quantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return CartItemMapper.toResponse(savedCartItem);
    }

    public void deleteCartItem(
            Long cartId,
            Long itemId) {

        // 1. Silinecek CartItem'ı bul
        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow();

        // 2. Bu CartItem gerçekten bu sepete mi ait?
        if (cartItem.getCart().getId() != cartId) {
            throw new RuntimeException("CartItem bu sepete ait değil");
        }

        // 3. DB'den sil
        cartItemRepository.delete(cartItem);
    }
}