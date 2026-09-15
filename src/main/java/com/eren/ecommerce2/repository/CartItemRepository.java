package com.eren.ecommerce2.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eren.ecommerce2.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
        Optional<CartItem> findByCartIdAndProductId(
        Long cartId,
        Long productId
        );

        List <CartItem> findByCartId(long cartId);
}

