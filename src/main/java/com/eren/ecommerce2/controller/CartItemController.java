package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.CartItemRequest;
import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.service.CartItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/{cartId}/items")
    public CartItemResponse addToCart(
            @PathVariable Long cartId,
            @RequestBody CartItemRequest request) {

        return cartItemService.addToCart(cartId, request);
    }
}