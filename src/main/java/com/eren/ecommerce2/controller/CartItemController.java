package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.CartItemRequest;
import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.service.CartItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    // PSEUDOCODE:
    // HTTP body'den cartId, productId ve quantity al.
    // Bunları CartItemRequest nesnesine çevir.
    // Service'teki addToCart işlemini çalıştır.
    // Dönen CartItemResponse'u client'a gönder.
    @PostMapping
    public CartItemResponse addToCart(
            @RequestBody CartItemRequest request) {

        return cartItemService.addToCart(request);
    }
}