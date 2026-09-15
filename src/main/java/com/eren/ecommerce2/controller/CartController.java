// CartController.java

package com.eren.ecommerce2.controller;

import com.eren.ecommerce2.dto.CartResponse;
import com.eren.ecommerce2.model.Cart;
import com.eren.ecommerce2.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public Cart createCart() {

        return cartService.createCart();
    }

    @GetMapping("/{cartId}")
    public CartResponse getCart(
            @PathVariable Long cartId) {

        return cartService.getCart(cartId);
    }

    
}