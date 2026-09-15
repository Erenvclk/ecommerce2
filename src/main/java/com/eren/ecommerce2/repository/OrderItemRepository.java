package com.eren.ecommerce2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eren.ecommerce2.model.OrderItem;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}