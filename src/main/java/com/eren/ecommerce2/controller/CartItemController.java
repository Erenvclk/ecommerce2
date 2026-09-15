package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.CartItemRequest;
import com.eren.ecommerce2.dto.CartItemResponse;
import com.eren.ecommerce2.dto.UpdateCartItemRequest;
import com.eren.ecommerce2.service.CartItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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

    @PatchMapping("/{cartId}/items/{itemId}")
    public CartItemResponse updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest request) {

        return cartItemService.updateQuantity(
                cartId,
                itemId,
                request);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(
            @PathVariable Long cartId,
            @PathVariable Long itemId) {

        cartItemService.deleteCartItem(
                cartId,
                itemId);
    }
}