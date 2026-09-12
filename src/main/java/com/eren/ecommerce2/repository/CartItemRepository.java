package com.eren.ecommerce2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eren.ecommerce2.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
